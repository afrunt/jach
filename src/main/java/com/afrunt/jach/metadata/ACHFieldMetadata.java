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
package com.afrunt.jach.metadata;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.InclusionRequirement;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.afrunt.jach.annotation.InclusionRequirement.*;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * @author Andrii Frunt
 */
public class ACHFieldMetadata implements Comparable<ACHFieldMetadata> {
    public static final Collection<Class<?>> VALID_FIELD_TYPES_SET = unmodifiableList(asList(Integer.class, Long.class,
            Short.class, Double.class, BigInteger.class, BigDecimal.class, String.class, Date.class));

    private String name;
    private Class<?> fieldType;
    private ACHField annotation;
    private Method getter;
    private Method setter;
    private String achFieldName;
    private boolean typeTag;
    private InclusionRequirement inclusion = OPTIONAL;
    private int start;
    private int length;
    private Set<String> values = new TreeSet<>();
    private String dateFormat;
    private int digitsAfterComma = 2;

    public ACHFieldMetadata() {
    }

    public ACHFieldMetadata(String name, Class<?> fieldType, ACHField annotation, Method getter, Method setter) {
        this.name = name;
        this.fieldType = fieldType;
        this.annotation = annotation;
        this.getter = getter;
        this.setter = setter;
    }

    public boolean fieldNameIs(String name) {
        return Optional.ofNullable(name)
                .map(n -> name.equals(this.name))
                .orElse(this.name == null);
    }

    public boolean typeIs(Class<?> fieldType) {
        return this.fieldType.equals(fieldType);
    }

    public boolean isString() {
        return typeIs(String.class);
    }

    public boolean isNumber() {
        return Number.class.isAssignableFrom(getFieldType());
    }

    public boolean isShort() {
        return typeIs(Short.class);
    }

    public boolean isInteger() {
        return typeIs(Integer.class);
    }

    public boolean isDouble() {
        return typeIs(Double.class);
    }

    public boolean isBigInteger() {
        return typeIs(BigInteger.class);
    }

    public boolean isLong() {
        return typeIs(Long.class);
    }

    public boolean isBigDecimal() {
        return typeIs(BigDecimal.class);
    }

    public boolean typeIsInstanceOf(Class<?> baseClass) {
        return this.fieldType.isAssignableFrom(baseClass);
    }

    public boolean positionIs(int start, int length) {
        return getStart() == start && getLength() == length;
    }

    public boolean isRecordTypeCode() {
        return fieldNameIs("recordTypeCode") && positionIs(0, 1);
    }

    public boolean valueSatisfies(String value) {
        if (isBlank()) {
            return "".equals(value.trim());
        } else if (hasConstantValues()) {
            return valueSatisfiesToConstantValues(value);
        } else {
            return valueSatisfiesToFormat(value);
        }
    }

    public boolean valueSatisfiesToFormat(String value) {
        if (value.length() != getLength()) {
            return false;
        }

        if (isString()) {
            return !isMandatory() || !"".equals(value.trim());
        } else if (isNumber()) {
            if (!isFractional() && value.contains(".")) {
                return false;
            }

            try {
                new BigDecimal(value.trim());
            } catch (Exception e) {
                return false;
            }
        } else if (isDate()) {
            SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
            try {
                sdf.parse(value);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean isDate() {
        return typeIs(Date.class);
    }

    public boolean isFractional() {
        return isNumber() && (typeIs(Double.class) || typeIs(BigDecimal.class));
    }

    public boolean valueSatisfiesToConstantValues(String value) {
        if (hasConstantValues()) {
            return getConstantValues().contains(value) || (isOptional() && "".equals(value.trim()));
        } else {
            return true;
        }
    }

    public boolean hasConstantValues() {
        return getValues().size() > 0;
    }


    public Set<String> getConstantValues() {
        return Collections.unmodifiableSet(getValues());
    }

    public boolean isReadOnly() {
        return setter == null;
    }

    public boolean inclusionRequirementIs(InclusionRequirement requirement) {
        return inclusion == requirement;
    }

    public boolean isRequired() {
        return inclusionRequirementIs(REQUIRED);
    }

    public boolean isMandatory() {
        return inclusionRequirementIs(MANDATORY);
    }

    public boolean isBlank() {
        return inclusionRequirementIs(BLANK);
    }

    public boolean isTypeTag() {
        return typeTag;
    }

    public ACHFieldMetadata setTypeTag(boolean typeTag) {
        this.typeTag = typeTag;
        return this;
    }

    public boolean isOptional() {
        return inclusionRequirementIs(OPTIONAL);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return start + length;
    }

    public int getLength() {
        return length;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public ACHField getAnnotation() {
        return annotation;
    }

    public ACHFieldMetadata setAnnotation(ACHField annotation) {
        this.annotation = annotation;
        return this;
    }

    public Method getGetter() {
        return getter;
    }

    public ACHFieldMetadata setGetter(Method getter) {
        this.getter = getter;
        return this;
    }

    public Method getSetter() {
        return setter;
    }

    public ACHFieldMetadata setSetter(Method setter) {
        this.setter = setter;
        return this;
    }

    public String getName() {
        return name;
    }

    public ACHFieldMetadata setName(String name) {
        this.name = name;
        return this;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public ACHFieldMetadata setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public List<String> getPossibleValues() {
        List<String> result = new ArrayList<>();

        result.addAll(getConstantValues());


        if (result.isEmpty()) {
            if (isString()) {
                result.add("Any string value with maximum length of " + getLength());
            } else if (isNumber()) {
                result.add("Any numeric value with maximum length of " + getLength());
            } else if (isDate()) {
                result.add("Any date value with with pattern " + getDateFormat());
            } else {
                result.add("DEADBEAF");
            }
        }

        return result;
    }


    public InclusionRequirement getInclusion() {
        return inclusion;
    }

    public ACHFieldMetadata setInclusion(InclusionRequirement inclusion) {
        this.inclusion = inclusion;
        return this;
    }

    public String getAchFieldName() {
        return achFieldName;
    }

    public ACHFieldMetadata setAchFieldName(String achFieldName) {
        this.achFieldName = achFieldName;
        return this;
    }

    public ACHFieldMetadata setStart(int start) {
        this.start = start;
        return this;
    }

    public ACHFieldMetadata setLength(int length) {
        this.length = length;
        return this;
    }

    public Set<String> getValues() {
        return values;
    }

    public ACHFieldMetadata setValues(Set<String> values) {
        this.values = values;
        return this;
    }

    public ACHFieldMetadata setValues(String[] values) {
        this.values = new HashSet<>(Arrays.asList(values));
        return this;
    }

    public ACHFieldMetadata setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public int getDigitsAfterComma() {
        return digitsAfterComma;
    }

    public ACHFieldMetadata setDigitsAfterComma(int digitsAfterComma) {
        this.digitsAfterComma = digitsAfterComma;
        return this;
    }

    @Override
    public String toString() {
        String typeName = fieldType.getName();
        return this.name + "[" + typeName.substring(typeName.lastIndexOf('.') + 1) + "]";
    }

    @Override
    public int compareTo(ACHFieldMetadata o) {
        return Integer.valueOf(getStart()).compareTo(o.getStart());
    }
}
