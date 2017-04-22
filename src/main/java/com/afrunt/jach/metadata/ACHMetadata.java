/**
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

import com.afrunt.jach.ACHException;
import com.afrunt.jach.domain.ACHRecord;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHMetadata {
    private Set<ACHRecordTypeMetadata> recordTypes;

    public ACHMetadata(Set<ACHRecordTypeMetadata> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public Set<ACHRecordTypeMetadata> typesForRecordTypeCode(String recordTypeCode) {
        return recordTypes.stream()
                .filter(rt -> rt.recordTypeCodeIs(recordTypeCode))
                .collect(Collectors.toSet());
    }

    public ACHRecordTypeMetadata typeOfRecord(ACHRecord record) {
        return recordTypes.stream()
                .filter(rt -> rt.getType().equals(record.getClass()))
                .findFirst()
                .orElseThrow(() -> new ACHException("Metadata not found for type " + record.getClass()));
    }
}
