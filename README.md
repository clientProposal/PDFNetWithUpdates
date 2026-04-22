## Getting Handwriting Sample Working

1. Navigate to the [Apryse Downloads page](https://dev.apryse.com/) 

2. Select your OS and download the Java sample from Step 3.

3. Unzip the folder.

4. Grab your trial key from the [Apryse Downloads page](https://dev.apryse.com/). If it doesn't appear, create an account and log in, or log in if you already have one.

5. With your OS selected, download the ICR module under "Modules" from the [Apryse Downloads page](https://dev.apryse.com/). 

6. Move the directory from the ICR module "HandwritingICR" (should be at HandwritingICRModuleMac/Samples/TestFiles/HandwritingICR
) to the subdirectory of the Java directory, you should end up with it at Samples/TestFiles/HandwritingICR. 

7. Navigate to Samples/HandwritingICRTest/JAVA of the Java directory and run the code. 

8. Find icr-prepended files in [this dir](Samples/TestFiles/Output)

## Sanitisation

Three examples of the [new functionality here](Samples/PDFSanitizeTest/JAVA/PDFSanitizeTest.java) 

🚩 1. CHECK IF PDF CONTAINS METADATA, MARKUPS/VISIBLE ANNOTATIONS OR HIDDEN LAYERS. 
THESE ARE THREE GROUPS OF SANITISABLE DATA.

🚩 2. REMOVES ALL SANITISABLE DATA. 

🚩 3. REMOVES A SUBSET OF SANITISABLE DATA.

Types of sanitisable data you can choose in your opts, see below: 

![the opts here](Samples/TestFiles/READMEImages/optsSanitisation.png).

## Getting MSG/EML Conversion Working

I used the PDF2OfficeTest as a basis, but I recreated it under [EmailToPdfConversion](Samples/EmailToPDFConversion)

We need both the structured output module (as we would with Office), as well as the HTML to PDF module. 

Download HTML to PDF Module [here](https://docs.apryse.com/core/guides/info/modules#html2pdf-module)

Download Structured Output Module on the [Apryse Downloads page](https://dev.apryse.com/).

## WebViewer Multiviewer Improvements

[See alternative repo](https://github.com/clientProposal/testMultiViewerFromUpdates).


## Digital Signing

Nothing new here, just recording points about some samples for reference. 

See [here](Samples/DigitalSignaturesTest/JAVA/DigitalSignaturesTest.java#L470)

e_ETSI_RFC3161: This field is used to check whether the digital signature field has been signed, but it is actually in reference to the timestamp.

This section shows us how to get back the MDP, as described in 12.8.2.2 of PDF Standard



