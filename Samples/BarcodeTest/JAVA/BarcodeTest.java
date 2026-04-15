//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.pdftron.pdf.*;

import com.pdftron.common.PDFNetException;

//---------------------------------------------------------------------------------------
// The Barcode Module is an optional PDFNet add-on that can be used to extract
// various types of barcodes from PDF documents.
//
// The Apryse SDK Barcode Module can be downloaded from https://dev.apryse.com/
//---------------------------------------------------------------------------------------
public class BarcodeTest {

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
			PDFNet.addResourceSearchPath("../../../Lib/");

			// Can optionally set path to the Barcode module
			if( !BarcodeModule.isModuleAvailable() )
			{
				System.out.println("");
				System.out.println("Unable to run BarcodeTest: Apryse SDK Barcode Module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Barcode Module is an optional add-on, available for download");
				System.out.println("at https://dev.apryse.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function.");
				System.out.println("");
				return;
			}

			// Relative path to the folder containing test files.
			String input_path = "../../TestFiles/Barcode/";
			String output_path = "../../TestFiles/Output/";

			//--------------------------------------------------------------------------------
			// Example 1) Detect and extract all barcodes from a PDF document into a JSON file
			System.out.println("Example 1: extracting barcodes from barcodes.pdf to barcodes.json");
				
			// A) Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "barcodes.pdf"))
			{
				// B) Detect PDF barcodes with the default options
				BarcodeModule.extractBarcodes(doc, output_path + "barcodes.json");

			} catch (Exception e) {
				e.printStackTrace();
			}

			//--------------------------------------------------------------------------------
			// Example 2) Limit barcode extraction to a range of pages, and retrieve the JSON into a
			// local string variable, which is then written to a file in a separate function call
			System.out.println("Example 2: extracting barcodes from pages 1-2 to barcodes_from_pages_1-2.json");
				
			// A) Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "barcodes.pdf"))
			{
				// B) Detect PDF barcodes with custom options
				BarcodeOptions options = new BarcodeOptions();

				// Convert only the first two pages
				options.setPages("1-2");

				String json = BarcodeModule.extractBarcodesAsString(doc, options);

				// C) Save JSON to file
				writeTextToFile(output_path + "barcodes_from_pages_1-2.json", json);

			} catch (Exception e) {
				e.printStackTrace();
			}

			//--------------------------------------------------------------------------------
			// Example 3) Narrow down barcode types and allow the detection of both horizontal
			// and vertical barcodes
			System.out.println("Example 3: extracting basic horizontal and vertical barcodes");
				
			// A) Open the .pdf document
			try (PDFDoc doc = new PDFDoc(input_path + "barcodes.pdf"))
			{
				// B) Detect only basic 1D barcodes, both horizontal and vertical
				BarcodeOptions options = new BarcodeOptions();

				// Limit extraction to basic 1D barcode types, such as EAN 13, EAN 8, UPCA, UPCE,
				// Code 3 of 9, Code 128, Code 2 of 5, Code 93, Code 11 and GS1 Databar.
				options.setBarcodeSearchTypes(BarcodeOptions.BarcodeTypeGroup.e_linear);

				// Search for barcodes oriented horizontally and vertically
				options.setBarcodeOrientations(
					BarcodeOptions.BarcodeOrientation.e_horizontal |
					BarcodeOptions.BarcodeOrientation.e_vertical);

				BarcodeModule.extractBarcodes(doc, output_path + "barcodes_1D.json", options);

			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Done.");
			PDFNet.terminate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
