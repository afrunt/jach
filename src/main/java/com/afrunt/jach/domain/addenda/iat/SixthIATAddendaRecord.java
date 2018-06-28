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
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "Sixth IAT Addenda Record")
public class SixthIATAddendaRecord extends IATAddendaRecord {

    public static final String SIXTH_IAT_ADDENDA_TYPE_CODE = "15";
    public static final String RECEIVER_IDENTIFICATION_NUMBER = "Receiver Identification Number";
    public static final String RECEIVER_STREET_ADDRESS = "Receiver Street Address";
    private String receiverIdentificationNumber;
    private String receiverStreetAddress;

    @Override
    @Values(SIXTH_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SIXTH_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 15, name = RECEIVER_IDENTIFICATION_NUMBER)
    public String getReceiverIdentificationNumber() {
        return receiverIdentificationNumber;
    }

    public SixthIATAddendaRecord setReceiverIdentificationNumber(String receiverIdentificationNumber) {
        this.receiverIdentificationNumber = receiverIdentificationNumber;
        return this;
    }

    @ACHField(start = 18, length = 35, name = RECEIVER_STREET_ADDRESS, inclusion = MANDATORY)
    public String getReceiverStreetAddress() {
        return receiverStreetAddress;
    }

    public SixthIATAddendaRecord setReceiverStreetAddress(String receiverStreetAddress) {
        this.receiverStreetAddress = receiverStreetAddress;
        return this;
    }

    @ACHField(start = 53, length = 34, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(34);
    }

}
