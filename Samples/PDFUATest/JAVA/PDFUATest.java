//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.pdf.pdfua.*;

//---------------------------------------------------------------------------------------
// The following sample illustrates how to make sure a file meets the PDF/UA standard, using the PDFUAConformance class object.
// Note: this feature is currently experimental and subject to change
//
// DataExtractionModule is required (Mac users can use StructuredOutputModule instead)
// https://docs.apryse.com/documentation/core/info/modules/#data-extraction-module
// https://docs.apryse.com/documentation/core/info/modules/#structured-output-module (Mac)
//---------------------------------------------------------------------------------------
public class PDFUATest {

    // Relative path to the folder containing test files.
    public static final String input_path = "../../TestFiles/";
    public static final String output_path = "../../TestFiles/Output/";

    // DataExtraction library location, replace if desired, should point to a folder that includes the contents of <DataExtractionModuleRoot>/Lib.
    // If using default, unzip the DataExtraction zip to the parent folder of Samples, and merge with existing "Lib" folder
    public static final String extraction_module_path = "../../../Lib/";

    public static void main(String[] args) {
        try {
            PDFNet.initialize(PDFTronLicense.Key());

            String input_file1 = input_path + "autotag_input.pdf";
            String input_file2 = input_path + "table.pdf";
            String output_file1 = output_path + "autotag_pdfua.pdf";
            String output_file2 = output_path + "table_pdfua_linearized.pdf";

            //-----------------------------------------------------------
            // Example: PDF/UA Conversion
            //-----------------------------------------------------------
            System.out.println("AutoConverting...");

            PDFNet.addResourceSearchPath(extraction_module_path);
            if(!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_doc_structure))
            {
                System.out.println("Unable to run PDFUATest: Apryse SDK Data Extraction module not available.");
                System.out.println("---------------------------------------------------------------");
                System.out.println("The Data Extraction module is an optional add-on, available for download");
                System.out.println("at https://apryse.com/. If you have already downloaded this");
                System.out.println("module, ensure that the SDK is able to find the required files");
                System.out.println("using the PDFNet::AddResourceSearchPath() function.");
                System.out.println("");
                return;
            }

            PDFUAConformance pdf_ua = new PDFUAConformance();

            System.out.println("Simple Conversion...");
            {
                // Perform conversion using default options
                pdf_ua.autoConvert(input_file1, output_file1);
            }

            System.out.println("Converting With Options...");
            {
                PDFUAOptions pdf_ua_opts = new PDFUAOptions();
                pdf_ua_opts.setSaveLinearized(true); // Linearize when saving output
                // Note: if file is password protected, you can use pdf_ua_opts.setPassword()

                // Perform conversion using the options we specify
                pdf_ua.autoConvert(input_file2, output_file2, pdf_ua_opts);
            }

        } catch (PDFNetException e) {
            System.out.println(e.getMessage());
        } finally {
            PDFNet.terminate();
            System.out.println("PDFUAConformance test completed.");
        }
    }

}
