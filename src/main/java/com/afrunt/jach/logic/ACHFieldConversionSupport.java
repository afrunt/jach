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

import com.afrunt.beanmetadata.FieldConversionSupport;
import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHFieldMetadata;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public interface ACHFieldConversionSupport extends FieldConversionSupport<ACHBeanMetadata, ACHFieldMetadata>, ACHErrorMixIn {
    default Integer valueStringToInteger(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return stringToBigDecimal(value, bm, fm).intValue();
    }

    default BigInteger valueStringToBigInteger(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return stringToBigDecimal(value, bm, fm).toBigInteger();
    }

    default Long valueStringToLong(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return stringToBigDecimal(value, bm, fm).longValue();
    }

    default Short valueStringToShort(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return stringToBigDecimal(value, bm, fm).shortValue();
    }

    default Date valueStringToDate(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        if (ACHField.EMPTY_DATE_PATTERN.equals(fm.getDateFormat())) {
            throwError("Date pattern should be specified for field " + fm);
        }
        try {
            return new SimpleDateFormat(fm.getDateFormat()).parse(value);
        } catch (ParseException e) {
            throw error("Error parsing date " + value + " with pattern " + fm.getDateFormat() + " for field " + fm, e);
        }
    }

    default BigDecimal valueStringToBigDecimal(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return moveDecimalRight(stringToBigDecimal(value, bm, fm), fm.getDigitsAfterComma());
    }

    default BigDecimal stringToBigDecimal(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        if (!StringUtil.isNumeric(value)) {
            throwError(String.format("Cannot parse string %s to number for field %s", value.trim(), fm));
        }

        return new BigDecimal(value.trim());
    }

    default String fieldStringToString(String value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padString(value, fm.getLength());
    }

    default String fieldShortToString(Short value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padNumber(value, fm.getLength());
    }

    default String fieldIntegerToString(Integer value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padNumber(value, fm.getLength());
    }

    default String fieldLongToString(Long value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padNumber(value, fm.getLength());
    }

    default String fieldBigIntegerToString(BigInteger value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padNumber(value, fm.getLength());
    }

    default String fieldBigDecimalToString(BigDecimal value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return padNumber(String.valueOf(
                moveDecimalLeft(value, fm.getDigitsAfterComma())
                        .longValue()), fm.getLength());
    }

    default String fieldDateToString(Date value, ACHBeanMetadata bm, ACHFieldMetadata fm) {
        return new SimpleDateFormat(fm.getDateFormat()).format(value);
    }

    default BigDecimal moveDecimalLeft(BigDecimal number, int digitsAfterComma) {
        return number.multiply(BigDecimal.TEN.pow(digitsAfterComma));
    }

    default BigDecimal moveDecimalRight(BigDecimal number, int digitsAfterComma) {
        return number.divide(BigDecimal.TEN.pow(digitsAfterComma));
    }

    default String padString(Object value, int length) {
        return StringUtil.rightPad(value.toString(), length);
    }

    default String padNumber(Object value, int length) {
        return StringUtil.leftPad(value.toString(), length, "0");
    }
}
