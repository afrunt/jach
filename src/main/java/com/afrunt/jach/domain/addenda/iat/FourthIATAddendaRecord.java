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
 * The Fourth Addenda Record contains information related to the financial institution originating the entry. For
 * inbound IAT entries, the Fourth Addenda Record must contain information to identify the foreign financial institution
 * that is providing the funding and payment instruction for the IAT entry.
 *
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "Fourth IAT Addenda Record")
public class FourthIATAddendaRecord extends IATAddendaRecord {
    public static final String FOURTH_IAT_ADDENDA_TYPE_CODE = "13";
    public static final String ORIGINATING_DFI_IDENTIFICATION = "ODFI Identification";
    public static final String ORIGINATING_DFI_BRANCH_COUNTRY_CODE = "ODFI Branch Country Code";
    public static final String ORIGINATING_DFI_IDENTIFICATION_NUMBER_QUALIFIER = "ODFI ID Number Qualifier";
    public static final String ORIGINATING_DFI_NAME = "Originating DFI Name";
    private String originatingDFIName;
    private String originatingDFIIdentificationNumberQualifier;
    private String originatingDFIIdentification;
    private String originatingDFIBranchCountryCode;

    @Override
    @Values(FOURTH_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return FOURTH_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, name = ORIGINATING_DFI_NAME, inclusion = MANDATORY)
    public String getOriginatingDFIName() {
        return originatingDFIName;
    }

    public FourthIATAddendaRecord setOriginatingDFIName(String originatorName) {
        this.originatingDFIName = originatorName;
        return this;
    }

    @ACHField(start = 38, length = 2, name = ORIGINATING_DFI_IDENTIFICATION_NUMBER_QUALIFIER, inclusion = MANDATORY,
            values = {"01", "02", "03"})
    public String getOriginatingDFIIdentificationNumberQualifier() {
        return originatingDFIIdentificationNumberQualifier;
    }

    public FourthIATAddendaRecord setOriginatingDFIIdentificationNumberQualifier(String originatingDFIIdentificationNumberQualifier) {
        this.originatingDFIIdentificationNumberQualifier = originatingDFIIdentificationNumberQualifier;
        return this;
    }

    @ACHField(start = 40, length = 34, name = ORIGINATING_DFI_IDENTIFICATION, inclusion = MANDATORY)
    public String getOriginatingDFIIdentification() {
        return originatingDFIIdentification;
    }

    public FourthIATAddendaRecord setOriginatingDFIIdentification(String originatingDFIIdentification) {
        this.originatingDFIIdentification = originatingDFIIdentification;
        return this;
    }

    @ACHField(start = 74, length = 3, name = ORIGINATING_DFI_BRANCH_COUNTRY_CODE, inclusion = MANDATORY)
    public String getOriginatingDFIBranchCountryCode() {
        return originatingDFIBranchCountryCode;
    }

    public FourthIATAddendaRecord setOriginatingDFIBranchCountryCode(String originatingDFIBranchCountryCode) {
        this.originatingDFIBranchCountryCode = originatingDFIBranchCountryCode;
        return this;
    }

    @ACHField(start = 77, length = 10, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(10);
    }

}
