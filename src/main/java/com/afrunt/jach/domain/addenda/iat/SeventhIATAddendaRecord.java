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
@ACHRecordType(name = "Seventh IAT Addenda Record")
public class SeventhIATAddendaRecord extends IATAddendaRecord {
    public static final String SEVENTH_IAT_ADDENDA_TYPE_CODE = "16";
    public static final String RECEIVER_CITY_STATE_PROVINCE = "Receiver City & State/Province";
    public static final String RECEIVER_COUNTRY_POSTAL_CODE = "Receiver Country & Postal Code";
    private String receiverCityAndStateProvince;
    private String receiverCountryAndPostalCode;

    @Override
    @Values(SEVENTH_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SEVENTH_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, name = RECEIVER_CITY_STATE_PROVINCE, inclusion = MANDATORY)
    public String getReceiverCityAndStateProvince() {
        return receiverCityAndStateProvince;
    }


    public SeventhIATAddendaRecord setReceiverCityAndStateProvince(String receiverCityAndStateProvince) {
        this.receiverCityAndStateProvince = receiverCityAndStateProvince;
        return this;
    }

    @ACHField(start = 38, length = 35, name = RECEIVER_COUNTRY_POSTAL_CODE, inclusion = MANDATORY)
    public String getReceiverCountryAndPostalCode() {
        return receiverCountryAndPostalCode;
    }

    public SeventhIATAddendaRecord setReceiverCountryAndPostalCode(String receiverCountryAndPostalCode) {
        this.receiverCountryAndPostalCode = receiverCountryAndPostalCode;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }
}
