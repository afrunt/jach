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
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.afrunt.jach.annotation.InclusionRequirement.*;
import static com.afrunt.jach.domain.RecordTypes.Constants.BATCH_CONTROL_RECORD_TYPE_CODE;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "Batch Control Record")
public class BatchControl extends ACHRecord {
    public static final String RECORD_TYPE_CODE = "Record Type Code";
    public static final String SERVICE_CLASS_CODE = "Service Class Code";
    public static final String ENTRY_ADDENDA_COUNT = "Entry Addenda Count";
    public static final String ENTRY_HASH = "Entry Hash";
    public static final String TOTAL_DEBITS = "Total Debits";
    public static final String TOTAL_CREDITS = "Total Credits";
    public static final String COMPANY_IDENTIFICATION = "Company Identification";
    public static final String MESSAGE_AUTHENTICATION_CODE = "Message Authentication Code";
    private Integer serviceClassCode;
    private Integer entryAddendaCount;
    private BigInteger entryHash;
    private BigDecimal totalDebits;
    private BigDecimal totalCredits;
    private String companyIdentification;
    private String messageAuthenticationCode;
    private String originatingDfiIdentification;
    private Integer batchNumber;

    @Override
    @Values(BATCH_CONTROL_RECORD_TYPE_CODE)
    public String getRecordTypeCode() {
        return BATCH_CONTROL_RECORD_TYPE_CODE;
    }

    @ACHField(start = 1, length = 3, name = SERVICE_CLASS_CODE, inclusion = MANDATORY, values = {"200", "220", "225", "280"})
    public Integer getServiceClassCode() {
        return this.serviceClassCode;
    }

    public BatchControl setServiceClassCode(Integer serviceClassCode) {
        this.serviceClassCode = serviceClassCode;
        return this;
    }

    @ACHField(start = 4, length = 6, name = ENTRY_ADDENDA_COUNT, inclusion = MANDATORY)
    public Integer getEntryAddendaCount() {
        return this.entryAddendaCount;
    }

    public BatchControl setEntryAddendaCount(Integer entryAddendaCount) {
        this.entryAddendaCount = entryAddendaCount;
        return this;
    }

    @ACHField(start = 10, length = 10, name = ENTRY_HASH, inclusion = MANDATORY)
    public BigInteger getEntryHash() {
        return this.entryHash;
    }

    public BatchControl setEntryHash(BigInteger entryHash) {
        this.entryHash = entryHash;
        return this;
    }

    @ACHField(start = 20, length = 12, name = TOTAL_DEBITS, inclusion = MANDATORY)
    public BigDecimal getTotalDebits() {
        return this.totalDebits;
    }

    public BatchControl setTotalDebits(BigDecimal totalDebits) {
        this.totalDebits = totalDebits;
        return this;
    }

    @ACHField(start = 32, length = 12, name = TOTAL_CREDITS, inclusion = MANDATORY)
    public BigDecimal getTotalCredits() {
        return this.totalCredits;
    }

    public BatchControl setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
        return this;
    }

    @ACHField(start = 44, length = 10, name = COMPANY_IDENTIFICATION, inclusion = MANDATORY)
    public String getCompanyIdentification() {
        return this.companyIdentification;
    }

    public BatchControl setCompanyIdentification(String companyIdentification) {
        this.companyIdentification = companyIdentification;
        return this;
    }

    @ACHField(start = 54, length = 19, name = MESSAGE_AUTHENTICATION_CODE, inclusion = OPTIONAL)
    public String getMessageAuthenticationCode() {
        return this.messageAuthenticationCode;
    }

    public BatchControl setMessageAuthenticationCode(String messageAuthenticationCode) {
        this.messageAuthenticationCode = messageAuthenticationCode;
        return this;
    }

    @ACHField(start = 73, length = 6, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(6);
    }

    @ACHField(start = 79, length = 8, name = "Originating DFI Identification", inclusion = MANDATORY)
    public String getOriginatingDfiIdentification() {
        return this.originatingDfiIdentification;
    }

    public BatchControl setOriginatingDfiIdentification(String originatingDfiIdentification) {
        this.originatingDfiIdentification = originatingDfiIdentification;
        return this;
    }

    @ACHField(start = 87, length = 7, name = "Batch Number", inclusion = MANDATORY)
    public Integer getBatchNumber() {
        return this.batchNumber;
    }

    public BatchControl setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }
}
