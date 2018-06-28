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

import com.afrunt.jach.annotation.ACHField;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public abstract class AddendaRecord extends ACHRecord {
    public static final String ADDENDA_RECORD_TYPE_CODE = "7";
    public static final String ENTRY_DETAIL_SEQUENCE_NUMBER = "Entry Detail Sequence Number";
    public static final String ADDENDA_TYPE_CODE = "Addenda Type Code";
    public static final String TRACE_NUMBER = "Trace Number";

    @Override
    @ACHField(length = 1, name = RECORD_TYPE_CODE, inclusion = MANDATORY, values = ADDENDA_RECORD_TYPE_CODE,
            typeTag = true)
    public String getRecordTypeCode() {
        return ADDENDA_RECORD_TYPE_CODE;
    }

    @ACHField(start = 1, length = 2, name = AddendaRecord.ADDENDA_TYPE_CODE, inclusion = MANDATORY, typeTag = true)
    public abstract String getAddendaTypeCode();


}
