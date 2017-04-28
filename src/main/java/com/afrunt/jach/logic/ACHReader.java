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

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.document.ACHBatch;
import com.afrunt.jach.document.ACHBatchDetail;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.domain.*;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHFieldMetadata;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.afrunt.jach.domain.RecordTypes.*;

/**
 * @author Andrii Frunt
 */
public class ACHReader extends ACHProcessor {
    public ACHReader(ACHMetadataCollector metadataCollector) {
        super(metadataCollector);
    }

    public ACHDocument read(InputStream is) {
        return new StatefulUnmarshaller().readDocument(is);
    }

    @SuppressWarnings("unchecked")
    public <T extends ACHRecord> T readRecord(String line, Class<T> recordClass) {
        return (T) readRecord(line, getMetadata().getBeanMetadata(recordClass));
    }

    protected List<String> splitString(String str, ACHBeanMetadata metadata) {
        return metadata.getACHFieldsMetadata().stream()
                .map(fm -> str.substring(fm.getStart(), fm.getEnd()))
                .collect(Collectors.toList());

    }

    protected ACHBeanMetadata typeOfRecord(String str) {
        str = validateLine(str);
        String recordTypeCode = extractRecordTypeCode(str);
        Set<ACHBeanMetadata> types = getMetadata().typesForRecordTypeCode(recordTypeCode);

        if (types.isEmpty()) {
            return null;
        } else {
            return getTypeWithHighestRate(rankTypes(str, types));
        }
    }

    protected String validateLine(String line) {
        if (line == null) {
            throw error("ACH record cannot be null");
        }

        int lineLength = line.length();

        if (lineLength != ACHRecord.ACH_RECORD_LENGTH) {
            throw error(String.format("Wrong length (%s) (line: %s) of the record: %s", lineLength, 0, line));
        }

        String recordTypeCode = extractRecordTypeCode(line);

        if (!RecordTypes.validRecordTypeCode(recordTypeCode)) {
            throw error(String.format("Unknown record type code (%s) (line: %s) of the record: %s", recordTypeCode, 0, line));
        }

        return line;
    }

    protected Set<ACHBeanMetadata> typesForRecordTypeCode(String recordTypeCode) {
        return getMetadata().typesForRecordTypeCode(recordTypeCode);
    }

    private int rankType(String str, ACHBeanMetadata beanMetadata) {
        List<String> strings = splitString(str, beanMetadata);
        List<ACHFieldMetadata> fms = new ArrayList<>(beanMetadata.getACHFieldsMetadata());
        return IntStream.range(0, strings.size())
                .map(i -> rankField(strings.get(i), fms.get(i)))
                .reduce(0, (left, right) -> left + right);
    }

    private ACHRecord readRecord(String line, ACHBeanMetadata recordType) {
        List<String> strings = splitString(line, recordType);

        ACHRecord record = (ACHRecord) recordType.createInstance();
        record.setRecord(line);

        int i = 0;

        for (ACHFieldMetadata fm : recordType.getACHFieldsMetadata()) {
            String valueString = strings.get(i);

            validateInputFieldValue(fm, valueString);

            if (!fm.isReadOnly()) {
                fm.applyValue(record, fieldValueFromString(valueString, fm));
            }

            i++;
        }

        return record;
    }

    protected Object fieldValueFromString(String value, ACHFieldMetadata fm) {
        if ("".equals(value.trim())) {
            return null;
        }

        if (fm.isString()) {
            return value;
        }

        if (fm.isNumber()) {
            if (StringUtil.isNumeric(value)) {
                return stringToNumber(value, fm);
            } else {
                throw error(String.format("Cannot parse string %s to number for field %s", value.trim(), fm));
            }
        } else if (fm.isDate()) {
            return dateValueFromString(value, fm);
        } else {
            throw error("Unsupported type " + fm.getType() + " of the field " + fm);
        }
    }

    protected void validateInputFieldValue(ACHFieldMetadata fm, String valueString) {
        boolean emptyOptional = fm.isOptional() && "".equals(valueString.trim());
        boolean valueNotSatisfiesToConstantValues = fm.hasConstantValues() && !fm.valueSatisfiesToConstantValues(valueString);
        if (valueNotSatisfiesToConstantValues && !emptyOptional) {
            throwError(String.format("%s is wrong value for field %s. Valid values are %s",
                    valueString, fm, StringUtil.join(fm.getPossibleValues(), ",")));
        }
    }

    private String extractRecordTypeCode(String line) {
        return line.substring(0, 1);
    }

    private Number stringToNumber(String value, ACHFieldMetadata fm) {
        BigDecimal number = new BigDecimal(value.trim());
        if (fm.isShort()) {
            return number.shortValue();
        } else if (fm.isInteger()) {
            return number.intValue();
        } else if (fm.isLong()) {
            return number.longValue();
        } else if (fm.isDouble()) {
            return moveDecimalRight(number, fm.getDigitsAfterComma()).doubleValue();
        } else if (fm.isBigInteger()) {
            return number.toBigInteger();
        } else if (fm.isBigDecimal()) {
            return moveDecimalRight(number, fm.getDigitsAfterComma());
        } else {
            throw error("Unsupported field type " + fm.getType() + " of the field " + fm);
        }
    }

    private Date dateValueFromString(String value, ACHFieldMetadata fm) {
        if (ACHField.EMPTY_DATE_PATTERN.equals(fm.getDateFormat())) {
            throwError("Date pattern should be specified for field " + fm);
        }
        try {
            return new SimpleDateFormat(fm.getDateFormat()).parse(value);
        } catch (ParseException e) {
            throw error("Error parsing date " + value + " with pattern " + fm.getDateFormat() + " for field " + fm, e);
        }
    }

    private ACHBeanMetadata getTypeWithHighestRate(Map<Integer, Set<ACHBeanMetadata>> rateMap) {
        Integer highestRate = rateMap.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElseThrow(() -> error("Type not found"));

        Set<ACHBeanMetadata> typesWithHighestRate = rateMap.get(highestRate);
        if (typesWithHighestRate.size() > 1) {
            throwError("More than one type found for string");
        }

        return typesWithHighestRate.stream()
                .findFirst()
                .orElseThrow(() -> error("Type not found"));
    }

    private Map<Integer, Set<ACHBeanMetadata>> rankTypes(String str, Set<ACHBeanMetadata> types) {
        Map<Integer, Set<ACHBeanMetadata>> result = new HashMap<>();

        for (ACHBeanMetadata type : types) {
            int rank = rankType(str, type);
            Set<ACHBeanMetadata> rankSet = result.getOrDefault(rank, new HashSet<>());
            rankSet.add(type);
            result.put(rank, rankSet);
        }

        return result;
    }

    private int rankField(String value, ACHFieldMetadata fieldMetadata) {
        if (fieldMetadata.isTypeTag()) {
            return fieldMetadata.valueSatisfiesToConstantValues(value) ? 1 : 0;
        } else {
            return 0;
        }
    }

    private class StatefulUnmarshaller {
        private int lineNumber = 0;
        private String currentLine;
        private ACHDocument document = new ACHDocument();
        private ACHBatch currentBatch;
        private ACHBatchDetail currentDetail;

        ACHDocument readDocument(InputStream is) {
            Scanner sc = new Scanner(is);

            while (sc.hasNextLine()) {
                ++lineNumber;
                currentLine = sc.nextLine();
                validateLine();

                ACHRecord record = readRecord(currentLine, findRecordType());

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

        private ACHBeanMetadata findRecordType() {
            if (!ENTRY_DETAIL.is(currentLine)) {
                return typeOfRecord(currentLine);
            } else {
                Set<ACHBeanMetadata> entryDetailTypes = getMetadata().typesForRecordTypeCode(ENTRY_DETAIL.getRecordTypeCode());
                String batchType = currentBatch.getBatchType();
                return entryDetailTypes.stream()
                        .filter(t -> t.getBeanClassName().startsWith(batchType))
                        .findFirst()
                        .orElseThrow(() -> error("Type of detail record not found for string: " + currentLine));
            }
        }

        private void throwValidationError(String message) {
            throwError("Line " + lineNumber + ". " + message);
        }
    }
}
