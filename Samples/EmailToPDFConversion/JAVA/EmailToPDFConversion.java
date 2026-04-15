//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.sdf.*;

//---------------------------------------------------------------------------------------
// The following sample illustrates how to use the PDF.Convert utility class to convert 
// MS Office files to PDF
//
// This conversion is performed entirely within the PDFNet and has *no* external or
// system dependencies dependencies -- Conversion results will be the same whether
// on Windows, Linux or Android.
//
// Please contact us if you have any questions. 
//---------------------------------------------------------------------------------------
public class EmailToPDFConversion {

    static String input_path = "../../TestFiles/";
    static String output_path = "../../TestFiles/Output/";

    public static void main(String[] args) {
        PDFNet.initialize(PDFTronLicense.Key());
        PDFNet.setResourcesPath("../../../Resources");

        try {
            HTML2PDF.setModulePath("../../../Lib");
            PDFNet.addResourceSearchPath("../../../Lib");
            if (!HTML2PDF.isModuleAvailable()) {
                System.out.println();
                System.out.println("Unable to run HTML2PDFTest: Apryse SDK HTML2PDF module not available.");
                System.out.println("---------------------------------------------------------------");
                System.out.println("The HTML2PDF module is an optional add-on, available for download");
                System.out.println("at https://www.pdftron.com/. If you have already downloaded this");
                System.out.println("module, ensure that the SDK is able to find the required files");
                System.out.println("using the HTML2PDF.setModulePath() function.");
                System.out.println();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // simpleDocxConvert("Fishermen.docx", "Fishermen.pdf");
        simpleDocxConvert("email_with_attachment_and_image_test.msg", "email_from_msg_with_attachment_and_image_test.pdf");
        simpleDocxConvert("example2Eml.eml", "email_from_eml_with_attachment_and_image_test.pdf");

        
        PDFNet.terminate();
    }

    public static void simpleDocxConvert(String inputFilename, String outputFilename) {
        try (PDFDoc pdfdoc = new PDFDoc()) {

            // perform the conversion with no optional parameters
            Convert.officeToPdf(pdfdoc, input_path + inputFilename, null);

            // save the result
            pdfdoc.save(output_path + outputFilename, SDFDoc.SaveMode.INCREMENTAL, null);
            // output PDF pdfdoc

            // And we're done!
            System.out.println("Done conversion " + output_path + outputFilename);
        } catch (PDFNetException e) {
            System.out.println("Unable to convert MS Office document, error:");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void flexibleDocxConvert(String inputFilename, String outputFilename) {
        try {
            OfficeToPDFOptions options = new OfficeToPDFOptions();
            options.setSmartSubstitutionPluginPath(input_path);

            // create a conversion object -- this sets things up but does not yet
            // perform any conversion logic.
            // in a multithreaded environment, this object can be used to monitor
            // the conversion progress and potentially cancel it as well
            DocumentConversion conversion = Convert.streamingPdfConversion(
                    input_path + inputFilename, options);

            System.out.println(inputFilename + ": " + Math.round(conversion.getProgress() * 100.0)
                    + "% " + conversion.getProgressLabel());

            // actually perform the conversion
            while (conversion.getConversionStatus() == DocumentConversion.e_incomplete) {
                conversion.convertNextPage();
                System.out.println(inputFilename + ": " + Math.round(conversion.getProgress() * 100.0)
                        + "% " + conversion.getProgressLabel());
            }

            if (conversion.tryConvert() == DocumentConversion.e_success) {
                int num_warnings = conversion.getNumWarnings();

                // print information about the conversion
                for (int i = 0; i < num_warnings; ++i) {
                    System.out.println("Warning: " + conversion.getWarningString(i));
                }

                // save the result
                try (PDFDoc doc = conversion.getDoc()) {
                    doc.save(output_path + outputFilename, SDFDoc.SaveMode.INCREMENTAL, null);
                }

                // done
                System.out.println("Done conversion " + output_path + outputFilename);
            } else {
                System.out.println("Encountered an error during conversion: " + conversion.getErrorString());
            }
        } catch (PDFNetException e) {
            System.out.println("Unable to convert MS Office document, error:");
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
