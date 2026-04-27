________________________________________________________________________________________________________________________

## Some notes about digital signature samples ##

________________________________________________________________________________________________________________________


1. See [here](Samples/DigitalSignaturesTest/JAVA/DigitalSignaturesTest.java#L470), e_ETSI_RFC3161

e_ETSI_RFC3161: This field is used to check whether the digital signature field has been signed, but it is actually in reference to the timestamp.

This section shows us how to get back the MDP, as described in 12.8.2.2 of PDF Standard

2. See [here](Samples/DigitalSignaturesTest/JAVA/DigitalSignaturesTest.java#L584), CustomSigningAPI

CustomSigningAPI serves as an example of how to carry out [PAdES-style](https://en.wikipedia.org/wiki/PAdES) externally signed workflow [QES](https://en.wikipedia.org/wiki/Qualified_electronic_signature) is carried out.

DigestAlgorithm.signDigest - this is used as an example, it's one way to get signature_value bytes via a PKCS#12 key.

Could be done with an algorithm coming from your HSM, to sign signedAttrs_digest. 

_Gives back a raw signature:_

```java 
DigitalSignatureField.generateCMSSignature
```

_Combine raw signature, certificates, algo info into CMS digital signature block_


```java 
DigitalSignatureField.generateCMSSignature
```

_Can then be embedded into PDF:_

```java 
doc.saveCustomSignature(cms_signature, digsig_field, output_path);
```

