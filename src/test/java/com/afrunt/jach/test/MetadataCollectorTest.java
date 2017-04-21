package com.afrunt.jach.test;

import com.afrunt.jach.domain.FileHeader;
import com.afrunt.jach.domain.detail.BOCEntryDetail;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.MetadataCollector;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author Andrii Frunt
 */
public class MetadataCollectorTest {
    @Test
    public void testMetadataCollection() {
        MetadataCollector metadataCollector = new MetadataCollector(Collections.singletonList("com.afrunt.jach.test"));
        List<ACHFieldMetadata> achFieldMetadatas = metadataCollector.collectExtFieldsMetadata(FileHeader.class);
        System.out.println();
    }
}
