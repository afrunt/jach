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

import com.afrunt.beanmetadata.FieldMetadata;
import com.afrunt.jach.annotation.*;
import com.afrunt.jach.logic.StringUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
public class ACHFieldMetadata extends FieldMetadata implements Comparable<ACHFieldMetadata> {
    private ACHField achFieldAnnotation;
    private InclusionRequirement inclusion;
    private List<String> values;
    private Integer start;
    private Integer end;
    private Integer length;
    private Boolean typeTag;
    private Boolean hasConstantValues;

    public boolean isACHField() {
        return achAnnotation() != null;
    }

    private ACHField achAnnotation() {
        if (achFieldAnnotation == null) {
            achFieldAnnotation = getAnnotation(ACHField.class);
        }
        return achFieldAnnotation;
    }

    public String getAchFieldName() {
        return achAnnotation().name();
    }

    public boolean isMandatory() {
        return inclusionIs(MANDATORY);
    }

    public boolean isOptional() {
        return inclusionIs(OPTIONAL);
    }

    public boolean isBlank() {
        return inclusionIs(BLANK);
    }

    public boolean isRequired() {
        return inclusionIs(REQUIRED);
    }

    public boolean inclusionIs(InclusionRequirement requirement) {
        return requirement.equals(getInclusionRequirement());
    }

    public InclusionRequirement getInclusionRequirement() {
        if (inclusion == null) {
            if (isAnnotatedWith(Inclusion.class)) {
                inclusion = getAnnotation(Inclusion.class).value();
            } else {
                inclusion = achAnnotation().inclusion();
            }
        }

        return inclusion;
    }

    public List<String> getValues() {
        if (values == null) {
            if (isAnnotatedWith(Values.class)) {
                values = Arrays.asList(getAnnotation(Values.class).value());
            } else {
                values = Arrays.asList(achAnnotation().values());
            }
        }

        return values;
    }

    public String getDateFormat() {
        return getOptionalAnnotation(DateFormat.class)
                .map(DateFormat::value)
                .orElse(achAnnotation().dateFormat());
    }

    public boolean isTypeTag() {
        if (typeTag == null) {
            typeTag = achAnnotation().typeTag();
        }
        return typeTag;
    }

    public int getStart() {
        if (start == null) {
            start = achAnnotation().start();
        }
        return start;
    }

    public int getLength() {
        if (length == null) {
            length = achAnnotation().length();
        }
        return length;
    }

    public int getEnd() {
        if (end == null) {
            end = getStart() + getLength();
        }
        return end;
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
            return true;
        } else if (isNumber()) {

            if (isOptional() && StringUtil.isBlank(value)) {
                return true;
            }

            if (!isFractional() && value.contains(".")) {
                return false;
            }

            try {
                if (BigDecimal.ZERO.compareTo(new BigDecimal(value.trim())) == 1) {
                    // Numbers should be positive or zero
                    return false;
                } else {
                    return true;
                }
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
    }

    public boolean valueSatisfiesToConstantValues(String value) {
        if (hasConstantValues()) {
            return getValues().contains(value) || (isOptional() && "".equals(value.trim()));
        } else {
            return true;
        }
    }

    public boolean hasConstantValues() {
        if (hasConstantValues == null) {
            hasConstantValues = getValues().size() > 0;
        }
        return hasConstantValues;
    }

    public int getDigitsAfterComma() {
        return 2;
    }

    public List<String> getPossibleValues() {
        List<String> result = new ArrayList<>();

        result.addAll(getValues());

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


    @Override
    public int compareTo(ACHFieldMetadata o) {
        return Integer.valueOf(getStart()).compareTo(o.getStart());
    }
}
