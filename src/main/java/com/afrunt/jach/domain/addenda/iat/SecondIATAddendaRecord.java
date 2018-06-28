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
package com.afrunt.jach.domain.addenda.iat;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
@ACHRecordType(name = "Second IAT Addenda Record")
public class SecondIATAddendaRecord extends IATAddendaRecord {
    public static final String SECOND_IAT_ADDENDA_TYPE_CODE = "11";
    public static final String ORIGINATOR_S_NAME = "Originator's Name";
    public static final String ORIGINATOR_S_PHYSICAL_ADDRESS = "Originator's physical address";

    private String originatorName;
    private String originatorStreetAddress;

    @Override
    @Values(SECOND_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SECOND_IAT_ADDENDA_TYPE_CODE;
    }

    /**
     * This field contains your company name
     *
     * @return
     */
    @ACHField(start = 3, length = 35, name = ORIGINATOR_S_NAME, inclusion = MANDATORY)
    public String getOriginatorName() {
        return originatorName;
    }

    public SecondIATAddendaRecord setOriginatorName(String originatorName) {
        this.originatorName = originatorName;
        return this;
    }

    /**
     * This field contains your company's address
     *
     * @return
     */
    @ACHField(start = 38, length = 35, name = ORIGINATOR_S_PHYSICAL_ADDRESS, inclusion = MANDATORY)
    public String getOriginatorStreetAddress() {
        return originatorStreetAddress;
    }

    public SecondIATAddendaRecord setOriginatorStreetAddress(String originatorStreetAddress) {
        this.originatorStreetAddress = originatorStreetAddress;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }
}
