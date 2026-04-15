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

// The following sample illustrates how to find and replace text in a document
public class FindReplaceTest {

	public static void main(String[] args) 
	{
		PDFNet.initialize(PDFTronLicense.Key());

		// Relative path to the folder containing test files.
		String input_path = "../../TestFiles/";
		String output_path = "../../TestFiles/Output/";

		// Open a PDF document to edit
		try (PDFDoc doc = new PDFDoc(input_path + "find-replace-test.pdf"))
		{
			FindReplaceOptions options = new FindReplaceOptions();

			// Set some find/replace options
			options.setWholeWords(true);
			options.setMatchCase(true);
			options.setMatchMode(FindReplaceOptions.MatchType.e_exact);
			options.setReflowMode(FindReplaceOptions.ReflowType.e_para);
			options.setAlignment(FindReplaceOptions.HorizAlignment.e_left);

			// Perform a Find/Replace finding "the" with "THE INCREDIBLE"
			FindReplace.findReplaceText(doc, "the", "THE INCREDIBLE", options);

			// Save the edited PDF
			doc.save(output_path + "find-replace-test-replaced.pdf", SDFDoc.SaveMode.LINEARIZED, null);
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
