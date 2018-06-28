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
import com.afrunt.jach.annotation.Values;

import java.math.BigDecimal;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.domain.RecordTypes.Constants.ENTRY_DETAIL_RECORD_TYPE_CODE;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public abstract class EntryDetail extends ACHRecord {
    public static final String TRANSACTION_CODE = "Transaction Code";
    public static final String RECEIVING_DFI_IDENTIFICATION = "RDFI ID";
    public static final String CHECK_DIGIT = "Check Digit";
    public static final String AMOUNT = "Amount";
    public static final String TRACE_NUMBER = "Trace Number";
    public static final String ADDENDA_RECORD_INDICATOR = "Addenda Record Indicator";

    private Integer transactionCode;
    private String receivingDfiIdentification;
    private Short checkDigit;
    private BigDecimal amount;

    private Short addendaRecordIndicator;
    private Long traceNumber;


    @Override
    @Values(ENTRY_DETAIL_RECORD_TYPE_CODE)
    public String getRecordTypeCode() {
        return ENTRY_DETAIL_RECORD_TYPE_CODE;
    }

    @ACHField(start = 1, length = 2, name = TRANSACTION_CODE, inclusion = MANDATORY)
    public Integer getTransactionCode() {
        return this.transactionCode;
    }

    public EntryDetail setTransactionCode(Integer transactionCode) {
        this.transactionCode = transactionCode;
        return this;
    }

    @ACHField(start = 3, length = 8, name = RECEIVING_DFI_IDENTIFICATION, inclusion = MANDATORY)
    public String getReceivingDfiIdentification() {
        return receivingDfiIdentification;
    }

    public EntryDetail setReceivingDfiIdentification(String receivingDfiIdentification) {
        this.receivingDfiIdentification = receivingDfiIdentification;
        return this;
    }

    @ACHField(start = 11, length = 1, name = CHECK_DIGIT, inclusion = MANDATORY)
    public Short getCheckDigit() {
        return this.checkDigit;
    }

    public EntryDetail setCheckDigit(Short checkDigit) {
        this.checkDigit = checkDigit;
        return this;
    }

    @ACHField(start = 29, length = 10, inclusion = MANDATORY, name = AMOUNT)
    public BigDecimal getAmount() {
        return this.amount;
    }

    public EntryDetail setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @ACHField(start = 78, length = 1, values = {"0", "1"}, inclusion = MANDATORY, name = ADDENDA_RECORD_INDICATOR)
    public Short getAddendaRecordIndicator() {
        return this.addendaRecordIndicator;
    }

    public EntryDetail setAddendaRecordIndicator(Short addendaRecordIndicator) {
        this.addendaRecordIndicator = addendaRecordIndicator;
        return this;
    }

    @ACHField(start = 79, length = 15, name = TRACE_NUMBER, inclusion = MANDATORY)
    public Long getTraceNumber() {
        return this.traceNumber;
    }

    public EntryDetail setTraceNumber(Long traceNumber) {
        this.traceNumber = traceNumber;
        return this;
    }
}
