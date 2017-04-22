package com.afrunt.jach;

import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.metadata.MetadataCollector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Andrii Frunt
 */
public class ACH {
    private MetadataCollector metadataCollector;
    private ACHUnmarshaller unmarshaller;
    private ACHMarshaller marshaller;

    public ACH() {
        metadataCollector = new MetadataCollector();
        unmarshaller = new ACHUnmarshaller(metadataCollector);
        marshaller = new ACHMarshaller(metadataCollector);
    }

    public ACHDocument read(InputStream is) {
        return unmarshaller.unmarshal(is);
    }

    public ACHDocument read(String string) {
        return unmarshaller.unmarshal(new ByteArrayInputStream(string.getBytes()));
    }

    public String write(ACHDocument document) {
        return marshaller.marshal(document);
    }

    public void write(ACHDocument document, OutputStream outputStream) {
        try {
            String str = marshaller.marshal(document);
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            throw new ACHException("Error writing ACH document to output stream", e);
        }

    }

}
