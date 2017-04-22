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
package com.afrunt.jach.logic;

import com.afrunt.jach.document.ACHBatch;
import com.afrunt.jach.document.ACHBatchDetail;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.domain.ACHRecord;
import com.afrunt.jach.domain.AddendaRecord;
import com.afrunt.jach.exception.ACHException;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHRecordTypeMetadata;
import com.afrunt.jach.metadata.MetadataCollector;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHMarshaller extends ACHProcessor {
    private Charset charset = Charset.forName("UTF-8");

    public ACHMarshaller(MetadataCollector metadataCollector) {
        super(metadataCollector);
    }

    private void marshal(ACHDocument document, OutputStream outputStream) {
        try {
            validateDocument(document);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            writer.write(marshalRecord(document.getFileHeader()));
            writer.write(LINE_SEPARATOR);

            document.getBatches().forEach(b -> marshalBatch(b, writer));

            writer.write(marshalRecord(document.getFileControl()));

            writer.flush();
        } catch (IOException e) {
            throw new ACHException(e);
        }
    }

    public String marshal(ACHDocument document) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            marshal(document, baos);
            return baos.toString(charset.name());
        } catch (IOException e) {
            throw new ACHException("Error marshalling ACH document", e);
        }
    }

    private void marshalBatch(ACHBatch batch, OutputStreamWriter writer) {
        try {
            validateBatch(batch);

            writer.write(marshalRecord(batch.getBatchHeader()));
            writer.write(LINE_SEPARATOR);

            batch.getDetails().forEach(d -> marshalBatchDetail(d, writer));

            writer.write(marshalRecord(batch.getBatchControl()));
            writer.write(LINE_SEPARATOR);
        } catch (IOException e) {
            throw new ACHException("Error marshalling batch", e);
        }
    }

    private void marshalBatchDetail(ACHBatchDetail detail, OutputStreamWriter writer) {
        try {
            writer.write(marshalRecord(detail.getDetailRecord()));
            writer.write(LINE_SEPARATOR);

            for (AddendaRecord addendaRecord : detail.getAddendaRecords()) {
                writer.write(marshalRecord(addendaRecord));
                writer.write(LINE_SEPARATOR);
            }

        } catch (IOException e) {
            throw new ACHException("Error marshalling details", e);
        }
    }

    private String marshalRecord(ACHRecord record) {
        String recordString = StringUtils.leftPad("", ACHRecord.ACH_RECORD_LENGTH);
        ACHRecordTypeMetadata typeMetadata = getMetadata().typeOfRecord(record);

        List<ACHFieldMetadata> fieldsMetadata = typeMetadata.getFieldsMetadata().stream()
                .sorted()
                .collect(Collectors.toList());

        for (ACHFieldMetadata fm : fieldsMetadata) {
            Object value = retrieveFieldValue(record, fm);
            String formattedValue = formatFieldValue(fm, value);
            String headString = recordString.substring(0, fm.getStart());
            String tailString = recordString.substring(fm.getEnd());
            recordString = headString + formattedValue + tailString;
        }


        return recordString;
    }

    private Object retrieveFieldValue(ACHRecord record, ACHFieldMetadata fm) {
        try {
            return fm.getGetter().invoke(record);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ACHException("Error retrieving value from field " + fm, e);
        }
    }

    private void validateDocument(ACHDocument document) {
        if (document == null) {
            error("Document cannot be null");
            return;
        }

        if (document.getFileHeader() == null) {
            error("File header cannot be null");
        }

        if (document.getFileControl() == null) {
            error("File control cannot be null");
        }

        if (document.getBatchCount() == 0) {
            error("At least one batch is required");
        }

    }

    private void validateBatch(ACHBatch batch) {
        if (batch.getBatchHeader() == null) {
            error("Batch header is required");
        }

        if (batch.getBatchControl() == null) {
            error("Batch control is required");
        }

        for (ACHBatchDetail detail : batch.getDetails()) {
            if (detail.getDetailRecord() == null) {
                error("Detail record is required");
            }
        }
    }
}
