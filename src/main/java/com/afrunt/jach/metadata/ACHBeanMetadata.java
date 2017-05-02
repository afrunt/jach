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

import com.afrunt.beanmetadata.BeanMetadata;
import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHBeanMetadata extends BeanMetadata<ACHFieldMetadata> {
    private Set<ACHFieldMetadata> achFieldsMetadata;
    private String recordTypeCode;
    private Set<ACHFieldMetadata> typeTagsMetadata;

    public String getACHRecordName() {
        return getAnnotation(ACHRecordType.class).name();
    }

    public Set<ACHFieldMetadata> getACHFieldsMetadata() {
        if (achFieldsMetadata == null) {
            achFieldsMetadata = new TreeSet<>(getFieldsAnnotatedWith(ACHField.class));
        }
        return achFieldsMetadata;
    }

    public Set<ACHFieldMetadata> getACHTypeTagsMetadata() {
        if (typeTagsMetadata == null) {
            typeTagsMetadata = new TreeSet<>(getACHFieldsMetadata().stream()
                    .filter(ACHFieldMetadata::isTypeTag)
                    .collect(Collectors.toSet()));
        }
        return typeTagsMetadata;
    }


    public boolean recordTypeCodeIs(String recordTypeCode) {
        return recordTypeCode.equals(getRecordTypeCode());
    }

    public String getRecordTypeCode() {
        if (recordTypeCode == null) {
            List<String> constantValues = getFieldMetadata("recordTypeCode").getValues();
            recordTypeCode = constantValues.get(0);
        }
        return recordTypeCode;
    }
}
