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
package com.afrunt.jach.domain;

import java.util.Arrays;

import static com.afrunt.jach.domain.RecordTypes.Constants.*;

/**
 * @author Andrii Frunt
 */
public enum RecordTypes {
    FILE_HEADER(FILE_HEADER_RECORD_TYPE_CODE), BATCH_HEADER(BATCH_HEADER_RECORD_TYPE_CODE),
    ENTRY_DETAIL(ENTRY_DETAIL_RECORD_TYPE_CODE), ADDENDA(ADDENDA_RECORD_TYPE_CODE),
    BATCH_CONTROL(BATCH_CONTROL_RECORD_TYPE_CODE), FILE_CONTROL(FILE_CONTROL_RECORD_TYPE_CODE);

    private final String recordTypeCode;

    RecordTypes(String recordTypeCode) {
        this.recordTypeCode = recordTypeCode;
    }

    public static boolean invalidRecordTypeCode(String recordTypeCode) {
        return Arrays.stream(RecordTypes.values())
                .noneMatch(rt -> rt.getRecordTypeCode().equals(recordTypeCode));
    }

    public String getRecordTypeCode() {
        return recordTypeCode;
    }

    public boolean is(String string) {
        return string.startsWith(recordTypeCode);
    }

    @SuppressWarnings("WeakerAccess")
    public static class Constants {
        public static final String FILE_HEADER_RECORD_TYPE_CODE = "1";
        public static final String BATCH_HEADER_RECORD_TYPE_CODE = "5";
        public static final String ENTRY_DETAIL_RECORD_TYPE_CODE = "6";
        public static final String ADDENDA_RECORD_TYPE_CODE = "7";
        public static final String BATCH_CONTROL_RECORD_TYPE_CODE = "8";
        public static final String FILE_CONTROL_RECORD_TYPE_CODE = "9";
    }
}
