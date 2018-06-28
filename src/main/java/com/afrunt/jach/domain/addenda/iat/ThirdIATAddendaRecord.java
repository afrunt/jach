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
@ACHRecordType(name = "Third IAT Addenda Record")
public class ThirdIATAddendaRecord extends IATAddendaRecord {
    public static final String THIRD_IAT_ADDENDA_TYPE_CODE = "12";
    public static final String ORIGINATOR_S_CITY_STATE_PROVINCE = "Originator's City & State/Province";
    public static final String ORIGINATOR_S_COUNTRY_POSTAL_CODE = "Originator's Country & Postal Code";

    private String originatorCityAndStateProvince;
    private String originatorCountryAndPostalCode;

    @Override
    @Values(THIRD_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return THIRD_IAT_ADDENDA_TYPE_CODE;
    }

    /**
     * City and State should be separated with an asterisk (*) as a delimiter and the field should end with a backslash
     * (\). For example: San Francisco*CA\.
     *
     * @return
     */
    @ACHField(start = 3, length = 35, name = ORIGINATOR_S_CITY_STATE_PROVINCE, inclusion = MANDATORY)
    public String getOriginatorCityAndStateProvince() {
        return originatorCityAndStateProvince;
    }

    public ThirdIATAddendaRecord setOriginatorCityAndStateProvince(String originatorName) {
        this.originatorCityAndStateProvince = originatorName;
        return this;
    }

    /**
     * Data elements must be separated by an asterisk (*) and must end with a backslash (\)
     * For example: US*10036\
     *
     * @return
     */
    @ACHField(start = 38, length = 35, name = ORIGINATOR_S_COUNTRY_POSTAL_CODE, inclusion = MANDATORY)
    public String getOriginatorCountryAndPostalCode() {
        return originatorCountryAndPostalCode;
    }

    public ThirdIATAddendaRecord setOriginatorCountryAndPostalCode(String originatorStreetAddress) {
        this.originatorCountryAndPostalCode = originatorStreetAddress;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }

}
