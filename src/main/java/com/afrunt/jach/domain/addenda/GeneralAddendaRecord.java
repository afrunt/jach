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
package com.afrunt.jach.domain.addenda;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.AddendaRecord;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.OPTIONAL;

/**
 * @author Andrii Frunt
 */
@ACHRecordType(name = "General CCD Addenda Record")
public class GeneralAddendaRecord extends AddendaRecord {
    private static final String CCD_ADDENDA_TYPE_CODE = "05";
    private static final String PAYMENT_RELATED_INFORMATION = "Payment Related Information";
    private static final String ADDENDA_SEQUENCE_NUMBER = "Addenda Sequence Number";

    private String paymentRelatedInformation;
    private Integer addendaSequenceNumber;

    private Long entryDetailSequenceNumber;

    @Override
    @Values(CCD_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return CCD_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 80, name = PAYMENT_RELATED_INFORMATION, inclusion = OPTIONAL)
    public String getPaymentRelatedInformation() {
        return paymentRelatedInformation;
    }

    public GeneralAddendaRecord setPaymentRelatedInformation(String paymentRelatedInformation) {
        this.paymentRelatedInformation = paymentRelatedInformation;
        return this;
    }

    @ACHField(start = 83, length = 4, name = ADDENDA_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Integer getAddendaSequenceNumber() {
        return addendaSequenceNumber;
    }

    public GeneralAddendaRecord setAddendaSequenceNumber(Integer addendaSequenceNumber) {
        this.addendaSequenceNumber = addendaSequenceNumber;
        return this;
    }

    @ACHField(start = 87, length = 7, name = ENTRY_DETAIL_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Long getEntryDetailSequenceNumber() {
        return entryDetailSequenceNumber;
    }

    public AddendaRecord setEntryDetailSequenceNumber(Long entryDetailSequenceNumber) {
        this.entryDetailSequenceNumber = entryDetailSequenceNumber;
        return this;
    }
}
