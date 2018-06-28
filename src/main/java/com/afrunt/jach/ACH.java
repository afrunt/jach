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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Andrii Frunt
 */
public class ACH implements ACHErrorMixIn {
    public static final String LINE_SEPARATOR = Optional.ofNullable(System.getProperty("line.separator")).orElse("\n");
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final Set<Class<?>> ACH_CLASSES = new HashSet<>(Arrays.asList(
            RemittanceIATAddendaRecord.class,
            SixthIATAddendaRecord.class,
            IATAddendaRecord.class,
            CORAddendaRecord.class,
            ThirdIATAddendaRecord.class,
            ForeignCorrespondentBankIATAddendaRecord.class,
            CIEEntryDetail.class,
            IATEntryDetail.class,
            RCKEntryDetail.class,
            GeneralBatchHeader.class,
            BatchControl.class,
            TELEntryDetail.class,
            FirstIATAddendaRecord.class,
            FileControl.class,
            BOCEntryDetail.class,
            ReturnAddendaRecord.class,
            SecondIATAddendaRecord.class,
            SeventhIATAddendaRecord.class,
            CTXEntryDetail.class,
            GeneralAddendaRecord.class,
            POPEntryDetail.class,
            FifthIATAddendaRecord.class,
            CCDEntryDetail.class,
            POSEntryDetail.class,
            POSAddendaRecord.class,
            FourthIATAddendaRecord.class,
            WEBEntryDetail.class,
            PPDEntryDetail.class,
            IATBatchHeader.class,
            FileHeader.class,
            ARCEntryDetail.class)
    );
    private final ACHMetadataCollector metadataCollector;
    private final ACHReader reader;
    private ACHWriter writer;
    private ACHMetadata metadata;
    private boolean blockAligning;

    public ACH() {
        metadataCollector = new ACHMetadataCollector();
        metadata = metadataCollector.collectMetadata(ACH_CLASSES);
        reader = new ACHReader(metadata);
        writer = new ACHWriter(metadata);
    }

    public <T extends ACHRecord> T readRecord(String line, Class<T> recordClass) {
        return reader.readRecord(line, recordClass);
    }

    public ACHDocument read(InputStream is) {
        return read(is, DEFAULT_CHARSET);
    }

    @SuppressWarnings("WeakerAccess")
    public ACHDocument read(InputStream is, Charset charset) {
        return reader.read(is, charset);
    }

    public ACHDocument read(String string) {
        return read(string, DEFAULT_CHARSET);
    }

    @SuppressWarnings("WeakerAccess")
    public ACHDocument read(String string, Charset charset) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(string.getBytes(charset))) {
            return reader.read(is);
        } catch (IOException e) {
            throw error("Error reading ACH document from string", e);
        }
    }

    public String write(ACHDocument document) {
        return write(document, DEFAULT_CHARSET);
    }

    @SuppressWarnings("WeakerAccess")
    public String write(ACHDocument document, Charset charset) {
        return writer.write(document, charset);
    }

    public void write(ACHDocument document, OutputStream outputStream) {
        write(document, outputStream, DEFAULT_CHARSET);
    }

    @SuppressWarnings("WeakerAccess")
    public void write(ACHDocument document, OutputStream outputStream, Charset charset) {
        try {
            String str = writer.write(document, charset);
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            throw error("Error writing ACH document to output stream", e);
        }
    }

    public ACHMetadata getMetadata() {
        if (metadata == null) {
            metadata = metadataCollector.collectMetadata(ACH_CLASSES);
        }
        return metadata;
    }

    public ACHReader getReader() {
        return reader;
    }

    public ACHWriter getWriter() {
        return writer;
    }

    public boolean withBlockAligning() {
        return blockAligning;
    }

    public ACH withBlockAligning(boolean value) {
        blockAligning = value;
        writer.setBlockAligning(blockAligning);
        return this;
    }
}
