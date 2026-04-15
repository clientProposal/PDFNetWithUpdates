//---------------------------------------------------------------------------------------
// Copyright (c) 2001-2026 by Apryse Software Inc. All Rights Reserved.
// Consult legal.txt regarding legal and license information.
//---------------------------------------------------------------------------------------

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;
import com.pdftron.filters.*;
import com.pdftron.sdf.SDFDoc;

//---------------------------------------------------------------------------------------
// The Data Extraction suite is an optional PDFNet add-on collection that can be used to
// extract various types of data from PDF documents.
//
// The Apryse SDK Data Extraction suite can be downloaded from http://www.pdftron.com/
//---------------------------------------------------------------------------------------

public class DataExtractionTest {

	static void writeTextToFile(String filename, String text) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(text);
		writer.close();
	}

	//---------------------------------------------------------------------------------------
	// The following sample illustrates how to extract tables from PDF documents.
	//---------------------------------------------------------------------------------------
	static void testTabularData()
	{
		try {
			// Test if the add-on is installed
			if (!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_tabular))
			{
				System.out.println();
				System.out.println("Unable to run Data Extraction: Apryse SDK Tabular Data module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Data Extraction suite is an optional add-on, available for download");
				System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function." );
				System.out.println();
				return;
			}
		} catch (PDFNetException e) {
			System.out.println("Data Extraction module not available, error:");
			e.printStackTrace();
			System.out.println(e);
		}

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		try {
			// Extract tabular data as a JSON file
			DataExtractionModule.extractData(input_path + "table.pdf", output_path + "table.json", DataExtractionModule.DataExtractionEngine.e_tabular);

			// Extract tabular data as a JSON string
			String json = DataExtractionModule.extractData(input_path + "financial.pdf", DataExtractionModule.DataExtractionEngine.e_tabular);
			writeTextToFile(output_path + "financial.json", json);

			// Extract tabular data as an XLSX file
			DataExtractionModule.extractToXLSX(input_path + "table.pdf", output_path + "table.xlsx");

			// Extract tabular data as an XLSX stream (also known as filter)
			DataExtractionOptions options = new DataExtractionOptions();
			options.setPages("1");
			MemoryFilter output_xlsx_stream = new MemoryFilter(0, false);
			DataExtractionModule.extractToXLSX(input_path + "financial.pdf", output_xlsx_stream, options);
			output_xlsx_stream.setAsInputFilter();
			output_xlsx_stream.writeToFile(output_path + "financial.xlsx", false);

		} catch (PDFNetException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	//---------------------------------------------------------------------------------------
	// The following sample illustrates how to extract document structure from PDF documents.
	//---------------------------------------------------------------------------------------
	static void testDocumentStructure()
	{
		// Test if the add-on is installed
		try {
			if (!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_doc_structure))
			{
				System.out.println();
				System.out.println("Unable to run Data Extraction: Apryse SDK Structured Output module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Data Extraction suite is an optional add-on, available for download");
				System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function." );
				System.out.println();
				return;
			}
		} catch (PDFNetException e) {
			System.out.println("Data Extraction module not available, error:");
			e.printStackTrace();
			System.out.println(e);
		}

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		try {
			// Extract document structure as a JSON file
			DataExtractionModule.extractData(input_path + "paragraphs_and_tables.pdf", output_path + "paragraphs_and_tables.json", DataExtractionModule.DataExtractionEngine.e_doc_structure);

			// Extract document structure as a JSON string
			String json = DataExtractionModule.extractData(input_path + "tagged.pdf", DataExtractionModule.DataExtractionEngine.e_doc_structure);
			writeTextToFile(output_path + "tagged.json", json);

		} catch (PDFNetException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	//---------------------------------------------------------------------------------------
	// The following sample illustrates how to extract form fields from PDF documents.
	//---------------------------------------------------------------------------------------
	static void testFormFields()
	{
		try {
			// Test if the add-on is installed
			if (!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_form))
			{
				System.out.println();
				System.out.println("Unable to run Data Extraction: Apryse SDK AIFormFieldExtractor module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Data Extraction suite is an optional add-on, available for download");
				System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function." );
				System.out.println();
				return;
			}
		} catch (PDFNetException e) {
			System.out.println("Data Extraction module not available, error:");
			e.printStackTrace();
			System.out.println(e);
		}

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		try {
			// Extract form fields as a JSON file
			DataExtractionModule.extractData(input_path + "formfields-scanned.pdf", output_path + "formfields-scanned.json", DataExtractionModule.DataExtractionEngine.e_form);

			// Extract form fields as a JSON string
			String json = DataExtractionModule.extractData(input_path + "formfields.pdf", DataExtractionModule.DataExtractionEngine.e_form);
			writeTextToFile(output_path + "formfields.json", json);

			//---------------------------------------------------------------------------------------
			// Detect and add form fields to a PDF document.
			// PDF document already has form fields, and this sample will update to new found fields.
			//---------------------------------------------------------------------------------------
			try (PDFDoc doc = new PDFDoc(input_path + "formfields-scanned-withfields.pdf"))
			{
				DataExtractionModule.detectAndAddFormFieldsToPDF(doc);

				// Save the modfied pdf document
				doc.save(output_path + "formfields-scanned-fields-new.pdf", SDFDoc.SaveMode.LINEARIZED, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//---------------------------------------------------------------------------------------
			// Detect and add form fields to a PDF document.
			// PDF document already has form fields, and this sample will keep the original fields.
			//---------------------------------------------------------------------------------------
			try (PDFDoc doc = new PDFDoc(input_path + "formfields-scanned-withfields.pdf"))
			{
				// Setup DataExtractionOptions to keep old fields
				DataExtractionOptions options = new DataExtractionOptions();
				options.setOverlappingFormFieldBehavior("KeepOld");

				DataExtractionModule.detectAndAddFormFieldsToPDF(doc, options);

				// Save the modfied pdf document
				doc.save(output_path + "formfields-scanned-fields-old.pdf", SDFDoc.SaveMode.LINEARIZED, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (PDFNetException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}

	//---------------------------------------------------------------------------------------
	// The following sample illustrates how to extract key-value pairs from PDF documents.
	//---------------------------------------------------------------------------------------
	public static void testGenericKeyValue() {
		try {
			// Test if the add-on is installed
			if (!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_form))
			{
				System.out.println();
				System.out.println("Unable to run Data Extraction: Apryse SDK AIPageObjectExtractor module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Data Extraction suite is an optional add-on, available for download");
				System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function." );
				System.out.println();
				return;
			}
		} catch (PDFNetException e) {
			System.out.println("Data Extraction module not available, error:");
			e.printStackTrace();
			System.out.println(e);
		}

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		try {

			// Simple example: Extract Keys & Values as a JSON file
			DataExtractionModule.extractData(input_path + "newsletter.pdf", output_path + "newsletter_key_val.json", DataExtractionModule.DataExtractionEngine.e_generic_key_value);

			// Example with customized options:
			// Extract Keys & Values from pages 2-4, excluding ads
			DataExtractionOptions options = new DataExtractionOptions();
			options.setPages("2-4");

			RectCollection p2ExclusionZones = new RectCollection();
			// Exclude the ad on page 2
			// These coordinates are in PDF user space, with the origin at the bottom left corner of the page
			// Coordinates rotate with the page, if it has rotation applied.
			p2ExclusionZones.addRect(166, 47, 562, 222);
			options.addExclusionZonesForPage(p2ExclusionZones, 2);

			RectCollection p4InclusionZones = new RectCollection();
			RectCollection p4ExclusionZones = new RectCollection();
			// Only include the article text for page 4, exclude ads and headings
			p4InclusionZones.addRect(30, 432, 562, 684);
			p4ExclusionZones.addRect(30, 657, 295, 684);
			options.addInclusionZonesForPage(p4InclusionZones, 4);
			options.addExclusionZonesForPage(p4ExclusionZones, 4);

			DataExtractionModule.extractData(input_path + "newsletter.pdf", output_path + "newsletter_key_val_with_zones.json", DataExtractionModule.DataExtractionEngine.e_generic_key_value, options);

		} catch (Exception e) {
			System.out.println(e);
		}        
    }

	//---------------------------------------------------------------------------------------
	// The following sample illustrates how to extract document classes from PDF documents.
	//---------------------------------------------------------------------------------------
	public static void testDocClassifier() {
		try {
			// Test if the add-on is installed
			if (!DataExtractionModule.isModuleAvailable(DataExtractionModule.DataExtractionEngine.e_doc_classification))
			{
				System.out.println();
				System.out.println("Unable to run Data Extraction: Apryse SDK AIPageObjectExtractor module not available.");
				System.out.println("---------------------------------------------------------------");
				System.out.println("The Data Extraction suite is an optional add-on, available for download");
				System.out.println("at http://www.pdftron.com/. If you have already downloaded this");
				System.out.println("module, ensure that the SDK is able to find the required files");
				System.out.println("using the PDFNet.addResourceSearchPath() function." );
				System.out.println();
				return;
			}
		} catch (PDFNetException e) {
			System.out.println("Data Extraction module not available, error:");
			e.printStackTrace();
			System.out.println(e);
		}

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		try {

			// Simple example: classify pages as a JSON file
			DataExtractionModule.extractData(input_path + "Invoice.pdf", output_path + "Invoice_Classified.json", DataExtractionModule.DataExtractionEngine.e_doc_classification);

			// Classify pages as a JSON string
			String json = DataExtractionModule.extractData(input_path + "Scientific_Publication.pdf", DataExtractionModule.DataExtractionEngine.e_doc_classification);
			writeTextToFile(output_path + "Scientific_Publication_Classified.json", json);

			// Example with customized options:
			DataExtractionOptions options = new DataExtractionOptions();
			// Classes that don't meet the minimum confidence threshold of 70% will not be listed in the output JSON
			options.setMinimumConfidenceThreshold(0.7);
			DataExtractionModule.extractData(input_path + "Email.pdf", output_path + "Email_Classified.json", DataExtractionModule.DataExtractionEngine.e_doc_classification, options);

		} catch (Exception e) {
			System.out.println(e);
		}        
    }

	public static void main(String[] args)
	{
		// The first step in every application using PDFNet is to initialize the 
		// library and set the path to common PDF resources. The library is usually 
		// initialized only once, but calling initialize() multiple times is also fine.
		PDFNet.initialize(PDFTronLicense.Key());
		PDFNet.addResourceSearchPath("../../../Lib/");

		testTabularData();
		testDocumentStructure();
		testFormFields();
		testGenericKeyValue();
		testDocClassifier();

		PDFNet.terminate();
	}
}
