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

import com.afrunt.beanmetadata.Metadata;
import com.afrunt.jach.annotation.ACHRecordType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHMetadata extends Metadata<ACHBeanMetadata, ACHFieldMetadata> {
    private final Map<String, Set<ACHBeanMetadata>> typesForRecordTypeCodeCache = new HashMap<>();
    private Set<ACHBeanMetadata> achBeansMetadata;

    @SuppressWarnings("WeakerAccess")
    public Set<ACHBeanMetadata> getACHBeansMetadata() {
        if (achBeansMetadata == null) {
            achBeansMetadata = getAnnotatedWith(ACHRecordType.class);
        }
        return achBeansMetadata;
    }

    public Set<ACHBeanMetadata> typesForRecordTypeCode(String recordTypeCode) {
        Set<ACHBeanMetadata> types;
        if (!typesForRecordTypeCodeCache.containsKey(recordTypeCode)) {
            types = getACHBeansMetadata().stream()
                    .filter(b -> b.recordTypeCodeIs(recordTypeCode))
                    .collect(Collectors.toSet());
            typesForRecordTypeCodeCache.put(recordTypeCode, types);
        } else {
            types = typesForRecordTypeCodeCache.get(recordTypeCode);
        }

        return types;
    }
}
