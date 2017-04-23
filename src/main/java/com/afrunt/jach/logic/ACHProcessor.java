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
import com.afrunt.jach.domain.ACHRecord;
import com.afrunt.jach.domain.RecordTypes;
import com.afrunt.jach.exception.ACHException;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHMetadata;
import com.afrunt.jach.metadata.ACHRecordTypeMetadata;
import com.afrunt.jach.metadata.MetadataCollector;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Andrii Frunt
 */
public class ACHProcessor {
    static final String LINE_SEPARATOR = Optional.ofNullable(System.getProperty("line.separator")).orElse("\n");

    private MetadataCollector metadataCollector;

    ACHProcessor(MetadataCollector metadataCollector) {
        this.metadataCollector = metadataCollector;
    }

    ACHRecordTypeMetadata typeOfRecord(String str) {
        str = validateLine(str, 0);
        String recordTypeCode = extractRecordTypeCode(str);
        Set<ACHRecordTypeMetadata> types = getMetadata().typesForRecordTypeCode(recordTypeCode);

        if (types.isEmpty()) {
            return null;
        } else {
            return getTypeWithHighestRate(rankTypes(str, types));
        }
    }

    Set<ACHRecordTypeMetadata> typesForRecordTypeCode(String recordTypeCode) {
        return getMetadata().typesForRecordTypeCode(recordTypeCode);
    }

    Object fieldValueFromString(String value, ACHFieldMetadata fm) {
        if ("".equals(value.trim())) {
            return null;
        }

        if (fm.isString()) {
            return value;
        }

        if (fm.isNumber()) {
            if (StringUtil.isNumeric(value)) {
                return numberFromString(value, fm);
            } else {
                throw error(String.format("Cannot parse string %s to number for field %s", value.trim(), fm));
            }
        } else if (fm.isDate()) {
            return dateValueFromString(value, fm);
        } else {
            throw error("Unsupported type " + fm.getFieldType() + " of the field " + fm);
        }
    }

    private String validateLine(String line, int lineNumber) {
        if (line == null) {
            throw error("ACH record cannot be null");
        }

        int lineLength = line.length();

        if (lineLength != ACHRecord.ACH_RECORD_LENGTH) {
            throw error(String.format("Wrong length (%s) (line: %s) of the record: %s", lineLength, lineNumber, line));
        }

        String recordTypeCode = extractRecordTypeCode(line);

        if (!RecordTypes.validRecordTypeCode(recordTypeCode)) {
            throw error(String.format("Unknown record type code (%s) (line: %s) of the record: %s", recordTypeCode, lineNumber, line));
        }

        return line;
    }

    String extractRecordTypeCode(String line) {
        return line.substring(0, 1);
    }

    public ACHMetadata getMetadata() {
        return metadataCollector.collectMetadata();
    }

    List<String> splitString(String str, ACHRecordTypeMetadata metadata) {
        return metadata.getFieldsMetadata().stream()
                .map(fm -> str.substring(fm.getStart(), fm.getEnd()))
                .collect(Collectors.toList());
    }

    void throwError(String message) throws ACHException {
        throw error(message);
    }

    protected ACHException error(String message) {
        return new ACHException(message);
    }

    protected ACHException error(String message, Throwable e) {
        return new ACHException(message, e);
    }

    String formatFieldValue(ACHFieldMetadata fm, Object value) {
        if (value == null) {
            return StringUtil.filledWithSpaces(fm.getLength());
        }

        if (fm.isString()) {
            return padString(value, fm.getLength());
        }

        if (fm.isDate()) {
            return new SimpleDateFormat(fm.getDateFormat()).format(value);
        }

        String stringValue = null;

        if (fm.isNumber()) {
            if (value instanceof BigDecimal) {
                stringValue = String.valueOf(((BigDecimal) value)
                        .multiply(BigDecimal.valueOf(10).pow(fm.getDigitsAfterComma()))
                        .longValue());
            } else if (value instanceof Double) {
                stringValue = String.valueOf(BigDecimal.valueOf((Double) value).multiply(BigDecimal.valueOf(10).pow(fm.getDigitsAfterComma()))
                        .longValue());
            } else {
                stringValue = value.toString();
            }

            if (stringValue.length() > fm.getLength()) {
                throw error("Value exceeds the maximum length of the field " + fm);
            }

            stringValue = StringUtil.leftPad(stringValue, fm.getLength(), "0");
        }

        return stringValue;
    }

    private String padString(Object value, int length) {
        return StringUtil.rightPad(value.toString(), length);
    }

    private Number numberFromString(String value, ACHFieldMetadata fm) {
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
            throw error("Unsupported field type " + fm.getFieldType() + " of the field " + fm);
        }
    }

    private BigDecimal moveDecimalRight(BigDecimal number, int digitsAfterComma) {
        return moveDecimal(number, n -> n.divide(decimalAdjuster(digitsAfterComma)));
    }

    private BigDecimal moveDecimal(BigDecimal number, Function<BigDecimal, BigDecimal> fn) {
        return fn.apply(number);
    }

    private BigDecimal decimalAdjuster(int digitsAfterComma) {
        return BigDecimal.TEN.pow(digitsAfterComma);
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

    private ACHRecordTypeMetadata getTypeWithHighestRate(Map<Integer, Set<ACHRecordTypeMetadata>> rateMap) {
        Integer highestRate = rateMap.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElseThrow(() -> error("Type not found"));

        Set<ACHRecordTypeMetadata> typesWithHighestRate = rateMap.get(highestRate);
        if (typesWithHighestRate.size() > 1) {
            throwError("More than one type found for string");
        }

        return typesWithHighestRate.stream()
                .findFirst()
                .orElseThrow(() -> error("Type not found"));
    }

    private Map<Integer, Set<ACHRecordTypeMetadata>> rankTypes(String str, Set<ACHRecordTypeMetadata> types) {
        Map<Integer, Set<ACHRecordTypeMetadata>> result = new HashMap<>();

        for (ACHRecordTypeMetadata type : types) {
            int rank = rankType(str, type);
            Set<ACHRecordTypeMetadata> rankSet = result.getOrDefault(rank, new HashSet<>());
            rankSet.add(type);
            result.put(rank, rankSet);
        }

        return result;
    }

    private int rankType(String str, ACHRecordTypeMetadata typeMetadata) {
        List<String> strings = splitString(str, typeMetadata);
        List<ACHFieldMetadata> fms = new ArrayList<>(typeMetadata.getFieldsMetadata());
        return IntStream.range(0, strings.size())
                .map(i -> rankField(strings.get(i), fms.get(i)))
                .reduce(0, (left, right) -> left + right);
    }

    private int rankField(String value, ACHFieldMetadata fieldMetadata) {
        if (fieldMetadata.isTypeTag()) {
            return fieldMetadata.valueSatisfiesToConstantValues(value) ? 1 : 0;
        } else {
            return 0;
        }
    }
}
