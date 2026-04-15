//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.sdf.SDFDoc;

//---------------------------------------------------------------------------------------
// The Handwriting ICR Module is an optional PDFNet add-on that can be used to extract
// handwriting from image-based pages and apply them as hidden text.
//
// The Apryse SDK Handwriting ICR Module can be downloaded from https://dev.apryse.com/
//---------------------------------------------------------------------------------------
public class HandwritingICRTest {

	static void writeTextToFile(String filename, String text) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(text);
		writer.close();
	}

	public static void main(String[] args) {
		try {
			// The first step in every application using PDFNet is to initialize the 
			// library and set the path to common PDF resources. The library is usually 
			// initialized only once, but calling Initialize() multiple times is also fine.
			PDFNet.initialize(PDFTronLicense.Key());

			// The location of the Handwriting ICR Module
			PDFNet.addResourceSearchPath("../../../Lib/HandwritingICRModuleMac/Lib/");

			// Test if the add-on is installed
			if (!HandwritingICRModule.isModuleAvailable())
			{
				System.out.println("");
				System.out.println("Unable to run HandwritingICRTest: Apryse SDK Handwriting ICR Module");
				System.out.println("not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Handwriting ICR Module is an optional add-on, available for download");
				System.out.println("at https://dev.apryse.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function.");
				System.out.println("");
				return;
			}

			// Relative path to the folder containing test files.
			String input_path = "../../TestFiles/HandwritingICR/";
			String output_path = "../../TestFiles/Output/";

			//--------------------------------------------------------------------------------
			// Example 1) Process a PDF without specifying options
			System.out.println("Example 1: processing icr.pdf");
				
			// Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "icr.pdf"))
			{
				// Run ICR on the .pdf with the default options
				HandwritingICRModule.processPDF(doc);

				// Save the result with hidden text applied
				doc.save(output_path + "icr-simple.pdf", SDFDoc.SaveMode.LINEARIZED, null);
				doc.close();
			} catch (PDFNetException e) {
				e.printStackTrace();
			}

			//--------------------------------------------------------------------------------
			// Example 2) Process a subset of PDF pages
			System.out.println("Example 2: processing pages from icr.pdf");
				
			// Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "icr.pdf"))
			{
				// Process handwriting with custom options
				HandwritingICROptions options = new HandwritingICROptions();

				// Optionally, process a subset of pages
				options.setPages("2-3");

				// Run ICR on the .pdf
				HandwritingICRModule.processPDF(doc, options);

				// Save the result with hidden text applied
				doc.save(output_path + "icr-pages.pdf", SDFDoc.SaveMode.LINEARIZED, null);
				doc.close();
			} catch (PDFNetException e) {
				e.printStackTrace();
			}

			//--------------------------------------------------------------------------------
			// Example 3) Ignore zones specified for each page
			System.out.println("Example 3: processing & ignoring zones");
				
			// Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "icr.pdf"))
			{
				// Process handwriting with custom options
				HandwritingICROptions options = new HandwritingICROptions();

				// Process page 2 by ignoring the signature area on the bottom
				options.setPages("2");
				RectCollection ignore_zones_page2 = new RectCollection();
				// These coordinates are in PDF user space, with the origin at the bottom left corner of the page.
				// Coordinates rotate with the page, if it has rotation applied.
				ignore_zones_page2.addRect(78, 850.1 - 770, 340, 850.1 - 676);
				options.addIgnoreZonesForPage(ignore_zones_page2, 2);

				// Run ICR on the .pdf
				HandwritingICRModule.processPDF(doc, options);

				// Save the result with hidden text applied
				doc.save(output_path + "icr-ignore.pdf", SDFDoc.SaveMode.LINEARIZED, null);
				doc.close();
			} catch (PDFNetException e) {
				e.printStackTrace();
			}

			//--------------------------------------------------------------------------------
			// Example 4) The postprocessing workflow has also an option of extracting ICR results
			// in JSON format, similar to the one used by the OCR Module
			System.out.println("Example 4: extract & apply");
				
			// Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "icr.pdf"))
			{
				// Extract ICR results in JSON format
				String json = HandwritingICRModule.getICRJsonFromPDF(doc);
				writeTextToFile(output_path + "icr-get.json", json);

				// Insert your post-processing step (whatever it might be)
				// ...

				// Apply potentially modified ICR JSON to the PDF
				HandwritingICRModule.applyICRJsonToPDF(doc, json);

				// Save the result with hidden text applied
				doc.save(output_path + "icr-get-apply.pdf", SDFDoc.SaveMode.LINEARIZED, null);
				doc.close();
			} catch (PDFNetException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				System.out.println(e);
			}
			System.out.println("Done.");
			PDFNet.terminate();
		} catch (PDFNetException e) {
			e.printStackTrace();
		}
	}
}
