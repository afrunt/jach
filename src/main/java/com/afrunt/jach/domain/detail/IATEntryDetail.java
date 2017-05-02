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
import com.afrunt.jach.domain.EntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@ACHRecordType(name = "IAT Entry Detail Record")
public class IATEntryDetail extends EntryDetail {
    private Short numberOfAddendaRecords;
    private String accountNumber;
    private String gatewayOperatorOFACScreeningIndicator;
    private String secondaryOFACScreeningIndicator;


    @ACHField(start = 12, length = 4, inclusion = MANDATORY, name = "Number of Addenda Records")
    public Short getNumberOfAddendaRecords() {
        return numberOfAddendaRecords;
    }

    public IATEntryDetail setNumberOfAddendaRecords(Short numberOfAddendaRecords) {
        this.numberOfAddendaRecords = numberOfAddendaRecords;
        return this;
    }

    @ACHField(start = 16, length = 13, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(13);
    }

    @ACHField(start = 39, length = 35, inclusion = MANDATORY, name = "Foreign Receiver's Account Number/DFI Account Number")
    public String getAccountNumber() {
        return accountNumber;
    }

    public IATEntryDetail setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    @ACHField(start = 74, length = 2, inclusion = BLANK, name = RESERVED)
    public String getBlank1() {
        return reserved(2);
    }

    @ACHField(start = 76, length = 1, name = "Gateway Operator OFAC Screening Indicator")
    public String getGatewayOperatorOFACScreeningIndicator() {
        return gatewayOperatorOFACScreeningIndicator;
    }

    public IATEntryDetail setGatewayOperatorOFACScreeningIndicator(String gatewayOperatorOFACScreeningIndicator) {
        this.gatewayOperatorOFACScreeningIndicator = gatewayOperatorOFACScreeningIndicator;
        return this;
    }

    @ACHField(start = 77, length = 1, name = "Secondary OFAC Screening Indicator")
    public String getSecondaryOFACScreeningIndicator() {
        return secondaryOFACScreeningIndicator;
    }

    public IATEntryDetail setSecondaryOFACScreeningIndicator(String secondaryOFACScreeningIndicator) {
        this.secondaryOFACScreeningIndicator = secondaryOFACScreeningIndicator;
        return this;
    }

    @Override
    @Values("1")
    public Short getAddendaRecordIndicator() {
        return 1;
    }
}
