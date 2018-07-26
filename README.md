[![Build Status](https://travis-ci.org/afrunt/jach.svg?branch=master)](https://travis-ci.org/afrunt/jach)
## Java library for reading and writing ACH documents 
Add jACH to your project. for maven projects just add this dependency:
```xml
<dependency>
  <groupId>com.afrunt</groupId>
  <artifactId>jach</artifactId>
  <version>0.2.5</version>
</dependency>
```
Using jACH you can work with ACH documents such as:
  * Read textual ACH documents to java domain model
  * Write java ACH domain model back to textual ACH format
  * Supported ACH formats are ARC, BOC, CBR, CCD, CIE, CTX, IAT, POP, POS, PPD, RCK, TEL, WEB
  
### Usage
Basically, to use jACH first instantiate the ACH class
```java
import com.afrunt.jach.ACH;
//...
ACH ach = new ACH();
//...
```
To parse the ACH document you simply need to pass the document's InputStream to read() method
```java
import com.afrunt.jach.document.ACHDocument;
//...
InputStream is = ...;
ACHDocument document = ach.read(is);
//...
```
To convert the java object model back to ACH document just pass the instance of ACHDocument to the write() method
```java
String out = ach.write(document);
```
or 
```java
OutputStream os = ...;
ach.write(document, os);
```
If you need to use specific character set, then you need to pass it as last parameter:
```java
Charset charset = Charset.forName("UTF-8");
ACHDocument document = ach.read(is, charset);
//.....
ach.write(document, os, charset);
```
