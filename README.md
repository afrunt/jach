## Java library for reading and writing ACH documents

### Usage
Basically, to use jACH first instantiate the ACH class
```java
ACH ach = new ACH();
```
To parse the ACH document you simply need to pass the document's InputStream to read() method
```java
InputStream is = ...;
ACHDocument document = ach.read(is);
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
