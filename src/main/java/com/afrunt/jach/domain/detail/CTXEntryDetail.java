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
package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
@ACHRecordType(name = "CTX Entry Detail Record")
public class CTXEntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private Short numberOfAddendaRecords;
    private String discretionaryData;
    private String receivingCompanyName;

    @Override
    @Values("1")
    public Short getAddendaRecordIndicator() {
        return 1;
    }

    @ACHField(start = 39, length = 15, name = NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public CTXEntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 4, inclusion = MANDATORY, name = "Number Of Addenda Records")
    public Short getNumberOfAddendaRecords() {
        return numberOfAddendaRecords;
    }

    public CTXEntryDetail setNumberOfAddendaRecords(Short numberOfAddendaRecords) {
        this.numberOfAddendaRecords = numberOfAddendaRecords;
        return this;
    }

    @ACHField(start = 58, length = 16, inclusion = REQUIRED, name = NonIATEntryDetail.RECEIVING_COMPANY_NAME)
    public String getReceivingCompanyName() {
        return receivingCompanyName;
    }

    public CTXEntryDetail setReceivingCompanyName(String receivingCompanyName) {
        this.receivingCompanyName = receivingCompanyName;
        return this;
    }

    @ACHField(start = 74, length = 2, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(2);
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public CTXEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
