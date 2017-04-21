package com.afrunt.jach.test;

import com.afrunt.jach.ACHUnmarshaller;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.metadata.MetadataCollector;
import org.junit.Test;

/**
 * @author Andrii Frunt
 */
public class ACHUnmarshallerTest {

    @Test
    public void testDocumentUnmarshalling() {
        MetadataCollector metadataCollector = new MetadataCollector();
        ACHUnmarshaller unmarshaller = new ACHUnmarshaller(metadataCollector);
        ACHDocument document = unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream("ach-iat.txt"));
        //ACHDocument document = unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream("ach.txt"));
        System.out.println();
    }

}
