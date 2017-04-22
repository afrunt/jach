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

import com.afrunt.jach.annotation.Field;
import com.afrunt.jach.annotation.RecordType;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@RecordType
public class FifthIATAddendaRecord extends IATAddendaRecord {
    public static final String FIFTH_ADDENDA_TYPE_CODE = "14";
    public static final String RDFI_S_NAME = "RDFI's name";
    public static final String RECEIVING_DFI_IDENTIFICATION_NUMBER_QUALIFIER = "Receiving DFI Identification Number Qualifier";
    public static final String RECEIVING_DFI_IDENTIFICATION_NUMBER = "Receiving DFI Identification Number";
    public static final String RECEIVING_DFI_BRANCH_COUNTRY_CODE = "Receiving DFI Branch Country Code";

    private String receivingDFIName;
    private String receivingDFIIDNumberQualifier;
    private String receivingDFIIDNumber;
    private String receivingDFIBranchCountryCode;

    @Override
    @Field(start = 1, length = 2, name = ADDENDA_TYPE_CODE, inclusion = MANDATORY,
            values = FIFTH_ADDENDA_TYPE_CODE, typeTag = true)
    public String getAddendaTypeCode() {
        return FIFTH_ADDENDA_TYPE_CODE;
    }

    @Field(start = 3, length = 35, name = RDFI_S_NAME, inclusion = MANDATORY)
    public String getReceivingDFIName() {
        return receivingDFIName;
    }

    public FifthIATAddendaRecord setReceivingDFIName(String receivingDFIName) {
        this.receivingDFIName = receivingDFIName;
        return this;
    }

    @Field(start = 38, length = 2, name = RECEIVING_DFI_IDENTIFICATION_NUMBER_QUALIFIER, inclusion = MANDATORY,
            values = {"01", "02", "03"})
    public String getReceivingDFIIDNumberQualifier() {
        return receivingDFIIDNumberQualifier;
    }

    public FifthIATAddendaRecord setReceivingDFIIDNumberQualifier(String receivingDFIIDNumberQualifier) {
        this.receivingDFIIDNumberQualifier = receivingDFIIDNumberQualifier;
        return this;
    }

    @Field(start = 40, length = 34, name = RECEIVING_DFI_IDENTIFICATION_NUMBER, inclusion = MANDATORY)
    public String getReceivingDFIIDNumber() {
        return receivingDFIIDNumber;
    }

    public FifthIATAddendaRecord setReceivingDFIIDNumber(String receivingDFIIDNumber) {
        this.receivingDFIIDNumber = receivingDFIIDNumber;
        return this;
    }

    @Field(start = 74, length = 3, name = RECEIVING_DFI_BRANCH_COUNTRY_CODE, inclusion = MANDATORY)
    public String getReceivingDFIBranchCountryCode() {
        return receivingDFIBranchCountryCode;
    }

    public FifthIATAddendaRecord setReceivingDFIBranchCountryCode(String receivingDFIBranchCountryCode) {
        this.receivingDFIBranchCountryCode = receivingDFIBranchCountryCode;
        return this;
    }

    @Field(start = 77, length = 10, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(10);
    }

}
