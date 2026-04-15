//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import java.lang.*;
import java.awt.*;

import com.pdftron.pdf.*;
import com.pdftron.sdf.SDFDoc;

//------------------------------------------------------------------------------
// PDFNet's Sanitizer is a security-focused feature that permanently removes
// hidden, sensitive, or potentially unsafe content from a PDF document.
// While redaction targets visible page content such as text or graphics,
// sanitization focuses on non-visual elements and embedded structures.
//
// PDFNet Sanitizer ensures hidden or inactive content is destroyed,
// not merely obscured or disabled. This prevents leakage of sensitive
// data such as authoring details, editing history, private identifiers,
// and residual form entries, and neutralizes scripts or attachments.
//
// Sanitization is recommended prior to external sharing with clients,
// partners, or regulatory bodies. It helps align with privacy policies
// and compliance requirements by permanently removing non-visual data.
//------------------------------------------------------------------------------
public class PDFSanitizeTest {

    public static void main(String[] args) {
        // Relative paths to folders containing test files.
        String input_path = "../../TestFiles/";
        String output_path = "../../TestFiles/Output/";

        PDFNet.initialize(PDFTronLicense.Key());


        // 🚩 1. CHECK IF PDF CONTAINS METADATA, MARKUPS/VISIBLE ANNOTATIONS OR HIDDEN LAYERS. 
        // THESE ARE THREE GROUPS OF SANITISABLE DATA.
        try (PDFDoc doc = new PDFDoc(input_path + "numbered.pdf")) {
            doc.initSecurityHandler();

            SanitizeOptions opts = Sanitizer.getSanitizableContent(doc);
            if (opts.getMetadata())
            {
                System.out.println("Document has metadata.");
            }
            if (opts.getMarkups())
            {
                System.out.println("Document has markups.");
            }
            if (opts.getHiddenLayers())
            {
                System.out.println("Document has hidden layers.");
            }
            System.out.println("Done...");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 🚩 2. REMOVES ALL SANITISABLE DATA. 

        try (PDFDoc doc = new PDFDoc(input_path + "financial.pdf")) {
            doc.initSecurityHandler();

            Sanitizer.sanitizeDocument(doc);
            doc.save(output_path + "financial_sanitized.pdf", SDFDoc.SaveMode.LINEARIZED, null);
            System.out.println("Done...");
        } catch (Exception e) {
            e.printStackTrace();
        }


	    // 🚩 3. REMOVES A SUBSET OF SANITISABLE DATA.

        try (PDFDoc doc = new PDFDoc(input_path + "form1.pdf")) {
            doc.initSecurityHandler();

            SanitizeOptions options = new SanitizeOptions();
            options.setMetadata(true);
            options.setFormData(true);
            options.setBookmarks(true);

            Sanitizer.sanitizeDocument(doc, options);
            doc.save(output_path + "form1_sanitized.pdf", SDFDoc.SaveMode.LINEARIZED, null);
            System.out.println("Done...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PDFNet.terminate();
    }
}