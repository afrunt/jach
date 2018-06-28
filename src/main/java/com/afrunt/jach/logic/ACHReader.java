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

import com.afrunt.jach.ACH;
import com.afrunt.jach.document.ACHBatch;
import com.afrunt.jach.document.ACHBatchDetail;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.domain.*;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHMetadata;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

import static com.afrunt.jach.domain.RecordTypes.*;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public class ACHReader extends ACHProcessor {
    public ACHReader(ACHMetadata metadata) {
        super(metadata);
    }

    public ACHDocument read(InputStream is) {
        return read(is, ACH.DEFAULT_CHARSET);
    }

    public ACHDocument read(InputStream is, Charset charset) {
        return new StatefulACHReader().readDocument(is, charset);
    }

    @SuppressWarnings("unchecked")
    public <T extends ACHRecord> T readRecord(String line, Class<T> recordClass) {
        return (T) readRecord(line, getMetadata().getBeanMetadata(recordClass));
    }

    public ACHRecord readRecord(String line, ACHBeanMetadata beanMetadata) {
        List<String> strings = splitString(line, beanMetadata);

        ACHRecord record = (ACHRecord) beanMetadata.createInstance();
        record.setRecord(line);

        int i = 0;

        for (ACHFieldMetadata fm : beanMetadata.getACHFieldsMetadata()) {
            String valueString = strings.get(i);

            validateInputFieldValue(fm, valueString);

            if (!fm.isReadOnly()) {
                Object value = "".equals(valueString.trim()) ? null : valueToField(valueString, String.class, beanMetadata, fm);
                fm.applyValue(record, value);
            }

            ++i;
        }

        return record;
    }

    public List<String> splitString(String str, ACHBeanMetadata metadata) {
        return splitString(str, metadata.getACHFieldsMetadata());
    }

    public List<String> splitString(String str, Collection<ACHFieldMetadata> achFieldsMetadata) {
        List<String> result = new ArrayList<>(achFieldsMetadata.size());
        for (ACHFieldMetadata fm : achFieldsMetadata) {
            result.add(str.substring(fm.getStart(), fm.getEnd()));
        }

        return result;
    }

    public ACHBeanMetadata typeOfString(String str) {
        str = validateString(str);
        String recordTypeCode = extractRecordTypeCode(str);
        Set<ACHBeanMetadata> types = getMetadata().typesForRecordTypeCode(recordTypeCode);

        if (types.isEmpty()) {
            return null;
        } else {
            return getTypeWithHighestRate(rankTypes(str, types));
        }
    }

    private String validateString(String line) {
        if (line == null) {
            throw error("ACH record cannot be null");
        }

        int lineLength = line.length();

        if (lineLength != ACHRecord.ACH_RECORD_LENGTH) {
            throwError(String.format("Wrong length (%s) (line: %s) of the record: %s", lineLength, 0, line));
        }

        String recordTypeCode = extractRecordTypeCode(line);

        if (RecordTypes.invalidRecordTypeCode(recordTypeCode)) {
            throwError(String.format("Unknown record type code (%s) (line: %s) of the record: %s", recordTypeCode, 0, line));
        }

        return line;
    }

    private void validateInputFieldValue(ACHFieldMetadata fm, String valueString) {
        boolean emptyOptional = fm.isOptional() && "".equals(valueString.trim());
        boolean valueNotSatisfiesToConstantValues = fm.hasConstantValues() && !fm.valueSatisfiesToConstantValues(valueString);
        if (valueNotSatisfiesToConstantValues && !emptyOptional) {
            throwError(String.format("%s is wrong value for field %s. Valid values are %s",
                    valueString, fm, StringUtil.join(fm.getPossibleValues(), ",")));
        }
    }

    private ACHBeanMetadata getTypeWithHighestRate(Map<Integer, Set<ACHBeanMetadata>> rateMap) {
        Set<ACHBeanMetadata> typesWithHighestRate = getTypesWithHighestRate(rateMap);

        int numberOfTypes = typesWithHighestRate.size();

        if (numberOfTypes == 1) {
            return typesWithHighestRate.iterator().next();
        } else if (numberOfTypes > 1) {
            throw error("More than one type found for string");
        } else {
            throw error("Type of the string not found");
        }
    }

    private Set<ACHBeanMetadata> getTypesWithHighestRate(Map<Integer, Set<ACHBeanMetadata>> rateMap) {
        int rateMapSize = rateMap.size();
        if (rateMapSize == 1) {
            return rateMap.values().iterator().next();
        } else if (rateMapSize > 1) {
            return rateMap.get(Collections.max(rateMap.keySet()));
        } else {
            throw error("Type of the string not found");
        }
    }

    private Map<Integer, Set<ACHBeanMetadata>> rankTypes(String str, Set<ACHBeanMetadata> types) {
        Map<Integer, Set<ACHBeanMetadata>> result = new HashMap<>(types.size());

        for (ACHBeanMetadata type : types) {
            int rank = rankType(str, type);
            if (rank > 0) {
                Set<ACHBeanMetadata> rankSet = result.getOrDefault(rank, new HashSet<>());
                rankSet.add(type);
                result.put(rank, rankSet);
            }
        }

        return result;
    }

    private int rankType(String str, ACHBeanMetadata beanMetadata) {
        List<ACHFieldMetadata> achTypeTagsMetadata = beanMetadata.getACHTypeTagsMetadata();
        List<String> strings = splitString(str, achTypeTagsMetadata);
        int rank = 0;
        int i = 0;
        for (ACHFieldMetadata fm : achTypeTagsMetadata) {
            String value = strings.get(i);
            rank += rankField(value, fm);
            ++i;
        }

        return rank;

        /*List<ACHFieldMetadata> fms = new ArrayList<>(beanMetadata.getACHFieldsMetadata());

        return IntStream.range(0, strings.size())
                .map(i -> rankField(strings.get(i), fms.get(i)))
                .reduce(0, (left, right) -> left + right);*/
    }

    private int rankField(String value, ACHFieldMetadata fieldMetadata) {
        return fieldMetadata.valueSatisfiesToConstantValues(value) ? 1 : 0;
    }

    private String extractRecordTypeCode(String line) {
        return line.substring(0, 1);
    }

    private class StatefulACHReader {
        private int lineNumber = 0;
        private String currentLine;
        private ACHDocument document = new ACHDocument();
        private ACHBatch currentBatch;
        private ACHBatchDetail currentDetail;

        ACHDocument readDocument(InputStream is, Charset charset) {
            Scanner sc = new Scanner(is, charset.name());

            while (sc.hasNextLine()) {
                ++lineNumber;
                currentLine = sc.nextLine();
                validateString();

                ACHRecord record = readRecord(currentLine, findRecordType())
                        .setLineNumber(lineNumber);

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

        ACHDocument readDocument(InputStream is) {
            return readDocument(is, ACH.DEFAULT_CHARSET);
        }

        private ACHDocument returnDocument() {
            ACHDocument currentDocument = document;
            //Unlink references
            document = null;
            currentBatch = null;
            currentDetail = null;
            currentLine = null;
            currentDocument.setNumberOfLines(lineNumber);
            return currentDocument;
        }

        private void validateString() {
            String recordTypeCode = extractRecordTypeCode(currentLine);

            if (currentLine.length() != ACHRecord.ACH_RECORD_LENGTH) {
                throwValidationError("Line length should be 94. Actual length is " + currentLine.length());
            }

            if (RecordTypes.invalidRecordTypeCode(recordTypeCode)) {
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

        private ACHBeanMetadata findRecordType() {
            if (!ENTRY_DETAIL.is(currentLine)) {
                return typeOfString(currentLine);
            } else {
                Set<ACHBeanMetadata> entryDetailTypes = getMetadata().typesForRecordTypeCode(ENTRY_DETAIL.getRecordTypeCode());
                String batchType = currentBatch.getBatchType();
                return entryDetailTypes.stream()
                        .filter(t -> t.getSimpleTypeName().startsWith(batchType))
                        .findFirst()
                        .orElseThrow(() -> error("Type of detail record not found for string: " + currentLine));
            }
        }

        private void throwValidationError(String message) {
            throwError("Line " + lineNumber + ". " + message);
        }
    }
}
