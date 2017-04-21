package com.afrunt.jach.test;

import com.afrunt.jach.ACHMarshaller;
import com.afrunt.jach.ACHUnmarshaller;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.metadata.MetadataCollector;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Andrii Frunt
 */
public class ACHMarshallerTest {
    private static final String[] ACH_FILES = {"ach.txt", "ach-iat.txt", "ach-return.txt", "ach-tr.txt","ach-payrol.txt"};

    @Test
    public void testMarshalling() {
        MetadataCollector metadataCollector = new MetadataCollector();
        ACHUnmarshaller unmarshaller = new ACHUnmarshaller(metadataCollector);

        ACHMarshaller marshaller = new ACHMarshaller(metadataCollector);


        for (String achFileName : ACH_FILES) {
            ACHDocument document = unmarshaller.unmarshal(getClass().getClassLoader().getResourceAsStream(achFileName));

            String out = marshaller.marshal(document);
            testFilesAreEquals(getClass().getClassLoader().getResourceAsStream(achFileName), new ByteArrayInputStream(out.getBytes()));
        }

    }

    private void testFilesAreEquals(InputStream is1, InputStream is2) {
        Scanner sc1 = new Scanner(is1);
        Scanner sc2 = new Scanner(is2);

        while (sc1.hasNextLine()) {
            String line1 = sc1.nextLine();
            String line2 = sc2.nextLine();
            Assert.assertEquals(line1, line2);
        }
    }
}
