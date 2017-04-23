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
import com.afrunt.jach.domain.*;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHRecordTypeMetadata;
import com.afrunt.jach.metadata.MetadataCollector;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.afrunt.jach.domain.RecordTypes.*;

/**
 * @author Andrii Frunt
 */
public class ACHUnmarshaller extends ACHProcessor {
    public ACHUnmarshaller(MetadataCollector metadataCollector) {
        super(metadataCollector);
    }

    public ACHDocument unmarshal(InputStream is) {
        return new StatefulUnmarshaller().unmarshalDocument(is);
    }

    @SuppressWarnings("unchecked")
    public <T extends ACHRecord> T unmarshalRecord(String line, Class<T> recordClass) {
        return (T) unmarshalRecord(line, getMetadata().typeForClass(recordClass));
    }

    private ACHRecord unmarshalRecord(String line, ACHRecordTypeMetadata recordType) {
        List<String> strings = splitString(line, recordType);

        ACHRecord record = recordType.createInstance(line);

        int i = 0;

        for (ACHFieldMetadata fm : recordType.getFieldsMetadata()) {
            String valueString = strings.get(i);

            validateInputValue(fm, valueString);

            if (!fm.isReadOnly()) {
                applyFieldValue(record, fm, fieldValueFromString(valueString, fm));
            }

            i++;
        }

        return record;
    }

    private void validateInputValue(ACHFieldMetadata fm, String valueString) {
        if (fm.hasConstantValues() && !fm.valueSatisfiesToConstantValues(valueString)) {
            throwError(String.format("%s is wrong value for field %s. Valid values are %s",
                    valueString, fm, StringUtil.join(fm.getPossibleValues(), ",")));
        }
    }

    private void applyFieldValue(ACHRecord record, ACHFieldMetadata fm, Object value) {
        try {
            Method setter = fm.getSetter();
            setter.invoke(record, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw error("Error applying value to field " + fm, e);
        }
    }

    private class StatefulUnmarshaller {
        private int lineNumber = 0;
        private String currentLine;
        private ACHDocument document = new ACHDocument();
        private ACHBatch currentBatch;
        private ACHBatchDetail currentDetail;

        ACHDocument unmarshalDocument(InputStream is) {
            Scanner sc = new Scanner(is);

            while (sc.hasNextLine()) {
                ++lineNumber;
                currentLine = sc.nextLine();
                validateLine();

                ACHRecord record = unmarshalRecord(currentLine, findRecordType());

                if (record.is(FILE_HEADER)) {
                    document.setFileHeader((FileHeader) record);
                } else if (record.is(FILE_CONTROL)) {
                    document.setFileControl((FileControl) record);
                    return returnDocument();
                } else if (record.is(BATCH_HEADER)) {
                    currentBatch = new ACHBatch().setBatchHeader((BatchHeader) record);
                    document.addBatch(currentBatch);
                } else if (record.is(BATCH_CONTROL)) {
                    currentBatch.setBatchControl((BatchControl) record);
                    currentBatch = null;
                    currentDetail = null;
                } else if (record.is(ENTRY_DETAIL)) {
                    currentDetail = new ACHBatchDetail()
                            .setDetailRecord((EntryDetail) record);
                    currentBatch.addDetail(currentDetail);

                } else if (record.is(ADDENDA)) {
                    currentDetail.addAddendaRecord((AddendaRecord) record);
                }
            }
            return returnDocument();
        }

        private ACHDocument returnDocument() {
            ACHDocument currentDocument = document;
            //Unlink references
            document = null;
            currentBatch = null;
            currentDetail = null;
            currentLine = null;
            return currentDocument;
        }

        private void validateLine() {
            String recordTypeCode = extractRecordTypeCode(currentLine);

            if (!RecordTypes.validRecordTypeCode(recordTypeCode)) {
                throwValidationError("Unknown record type code " + recordTypeCode);
            }

            if ((lineNumber == 1) && !FILE_HEADER.is(currentLine)) {
                throwValidationError("First line should be the file header, that start with 1");
            }

            if (RecordTypes.BATCH_CONTROL.is(currentLine) && currentBatch == null) {
                throwValidationError("Unexpected batch control record");
            }

            if (RecordTypes.BATCH_HEADER.is(currentLine) && currentBatch != null) {
                throwValidationError("Unexpected batch header record");
            }

            if (RecordTypes.ENTRY_DETAIL.is(currentLine) && currentBatch == null) {
                throwValidationError("Unexpected entry detail record");
            }

            if (RecordTypes.ADDENDA.is(currentLine) && currentDetail == null) {
                throwValidationError("Unexpected addenda record");
            }
        }

        private ACHRecordTypeMetadata findRecordType() {
            if (!ENTRY_DETAIL.is(currentLine)) {
                return typeOfRecord(currentLine);
            } else {
                Set<ACHRecordTypeMetadata> entryDetailTypes = typesForRecordTypeCode(ENTRY_DETAIL.getRecordTypeCode());
                String batchType = currentBatch.getBatchType();
                return entryDetailTypes.stream()
                        .filter(t -> t.getRecordClassName().startsWith(batchType))
                        .findFirst()
                        .orElseThrow(() -> error("Type of detail record not found for string: " + currentLine));
            }
        }

        private void throwValidationError(String message) {
            throwError("Line " + lineNumber + ". " + message);
        }
    }
}
