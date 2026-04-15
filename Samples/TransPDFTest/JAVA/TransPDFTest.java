//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.sdf.SDFDoc;

// The following sample illustrates how to extract xlf from a PDF document for translation.
// It then applies a pre-prepared translated xlf file to the PDF to produce a translated PDF. 
public class TransPDFTest {

	public static void main(String[] args) 
	{
		PDFNet.initialize(PDFTronLicense.Key());

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		// Open a PDF document to translate
		try (PDFDoc doc = new PDFDoc(input_path + "find-replace-test.pdf"))
		{
			TransPDFOptions options = new TransPDFOptions();

			// Set the source language in the options
			options.setSourceLanguage("en");

			// Set the number of pages to process in each batch
			options.setBatchSize(20);

			// Optionally, subset the pages to process
			// This PDF only has a single page, but you can specify a subset of pages like this
			// options.setPages("-2,5-6,9,11-");

			// Optionally, set the XLIFF exported version, default is 1.2
			// options.setXLIFFVersion(TransPDFOptions.XLIFFVersion.e_xliff_version_2);

			// Extract the xlf to file and field the PDF for translation
			TransPDF.extractXLIFF(doc, output_path + "find-replace-test.xlf", options);

			// Save the fielded PDF
			doc.save(output_path + "find-replace-test-fielded.pdf", SDFDoc.SaveMode.LINEARIZED, null);

			// The extracted xlf can be translated in a system of your choice.
			// In this sample a pre-prepared translated file is used - find-replace-test_(en_to_fr).xlf

			// Perform the translation using the pre-prepared translated xliff
			TransPDF.applyXLIFF(doc, input_path + "find-replace-test_(en_to_fr).xlf", options);

			// Save the translated PDF
			doc.save(output_path + "find-replace-test-fr.pdf", SDFDoc.SaveMode.LINEARIZED, null);
			doc.close();
		}
		catch (PDFNetException e)
		{
			e.printStackTrace();
			System.out.println(e);
		}

		PDFNet.terminate();
	}
}
