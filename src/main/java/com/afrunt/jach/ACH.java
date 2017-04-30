/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.afrunt.jach;

import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.domain.*;
import com.afrunt.jach.domain.addenda.CORAddendaRecord;
import com.afrunt.jach.domain.addenda.GeneralAddendaRecord;
import com.afrunt.jach.domain.addenda.POSAddendaRecord;
import com.afrunt.jach.domain.addenda.ReturnAddendaRecord;
import com.afrunt.jach.domain.addenda.iat.*;
import com.afrunt.jach.domain.detail.*;
import com.afrunt.jach.logic.ACHErrorMixIn;
import com.afrunt.jach.logic.ACHMetadataCollector;
import com.afrunt.jach.logic.ACHReader;
import com.afrunt.jach.logic.ACHWriter;
import com.afrunt.jach.metadata.ACHMetadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Andrii Frunt
 */
public class ACH implements ACHErrorMixIn {
    public static final HashSet<Class<?>> ACH_CLASSES = new HashSet<>(Arrays.asList(
            EntryDetail.class,
            RemittanceIATAddendaRecord.class,
            AddendaRecord.class,
            SixthIATAddendaRecord.class,
            IATAddendaRecord.class,
            CORAddendaRecord.class,
            ThirdIATAddendaRecord.class,
            ForeignCorrespondentBankIATAddendaRecord.class,
            IATEntryDetail.class,
            RCKEntryDetail.class,
            GeneralBatchHeader.class,
            BatchControl.class,
            TELEntryDetail.class,
            NonIATEntryDetail.class,
            FirstIATAddendaRecord.class,
            FileControl.class,
            BOCEntryDetail.class,
            ReturnAddendaRecord.class,
            BatchHeader.class,
            SecondIATAddendaRecord.class,
            SeventhIATAddendaRecord.class,
            CTXEntryDetail.class,
            GeneralAddendaRecord.class,
            POPEntryDetail.class,
            FifthIATAddendaRecord.class,
            CCDEntryDetail.class,
            POSAddendaRecord.class,
            FourthIATAddendaRecord.class,
            WEBEntryDetail.class,
            PPDEntryDetail.class,
            IATBatchHeader.class,
            FileHeader.class,
            ARCEntryDetail.class)
    );
    private ACHMetadataCollector metadataCollector;
    private ACHReader unmarshaller;
    private ACHWriter marshaller;

    public ACH() {
        metadataCollector = new ACHMetadataCollector();
        unmarshaller = new ACHReader(metadataCollector);
        marshaller = new ACHWriter(metadataCollector);
    }

    public <T extends ACHRecord> T readRecord(String line, Class<T> recordClass) {
        return unmarshaller.readRecord(line, recordClass);
    }

    public ACHDocument read(InputStream is) {
        return unmarshaller.read(is);
    }

    public ACHDocument read(String string) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(string.getBytes())) {
            return unmarshaller.read(is);
        } catch (IOException e) {
            throw error("Error reading ACH document from string", e);
        }
    }

    public String write(ACHDocument document) {
        return marshaller.write(document);
    }

    public void write(ACHDocument document, OutputStream outputStream) {
        try {
            String str = marshaller.write(document);
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            throw error("Error writing ACH document to output stream", e);
        }

    }

    public ACHMetadata getMetadata() {
        return metadataCollector.collectMetadata(ACH_CLASSES);
    }

    public ACH setFailWritingOnWrongValues(boolean failOnWrongValues) {
        marshaller.setFailOnWrongValues(failOnWrongValues);
        return this;
    }
}
