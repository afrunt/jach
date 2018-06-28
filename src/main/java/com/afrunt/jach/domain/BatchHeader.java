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
import com.afrunt.jach.annotation.DateFormat;
import com.afrunt.jach.annotation.Values;

import java.util.Date;

import static com.afrunt.jach.annotation.InclusionRequirement.*;
import static com.afrunt.jach.domain.RecordTypes.Constants.BATCH_HEADER_RECORD_TYPE_CODE;

/**
 * A batch is a collection of like entries within a file. You must use a separate batch if any of the batch-level
 * information, such as effective date or company name or company description changes.
 *
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public abstract class BatchHeader extends ACHRecord {
    public static final String SERVICE_CLASS_CODE = "Service Class Code";
    public static final String STANDARD_ENTRY_CLASS_CODE = "Standard Entry Class Code";
    public static final String COMPANY_ENTRY_DESCRIPTION = "Company Entry Description";
    public static final String EFFECTIVE_ENTRY_DATE = "Effective Entry Date";
    public static final String SETTLEMENT_DATE = "Settlement Date";
    public static final String ORIGINATOR_STATUS_CODE = "Originator Status Code";
    public static final String ORIGINATOR_DFI_IDENTIFIER = "Originator DFI Identifier";
    public static final String BATCH_NUMBER = "Batch Number";

    private String serviceClassCode;
    private String standardEntryClassCode;
    private String companyEntryDescription;
    private Date effectiveEntryDate;
    private Short settlementDate;
    private String originatorStatusCode;
    private String originatorDFIIdentifier;
    private Integer batchNumber;

    @Override
    @Values(BATCH_HEADER_RECORD_TYPE_CODE)
    public String getRecordTypeCode() {
        return BATCH_HEADER_RECORD_TYPE_CODE;
    }

    @ACHField(start = 1, length = 3, name = SERVICE_CLASS_CODE, inclusion = MANDATORY, values = {"200", "220", "225", "280"})
    public String getServiceClassCode() {
        return serviceClassCode;
    }

    public BatchHeader setServiceClassCode(String serviceClassCode) {
        this.serviceClassCode = serviceClassCode;
        return this;
    }

    @ACHField(start = 50, length = 3, name = STANDARD_ENTRY_CLASS_CODE, inclusion = MANDATORY, typeTag = true)
    public String getStandardEntryClassCode() {
        return standardEntryClassCode;
    }

    public BatchHeader setStandardEntryClassCode(String standardEntryClassCode) {
        this.standardEntryClassCode = standardEntryClassCode;
        return this;
    }

    @ACHField(start = 53, length = 10, name = COMPANY_ENTRY_DESCRIPTION, inclusion = MANDATORY)
    public String getCompanyEntryDescription() {
        return companyEntryDescription;
    }

    public BatchHeader setCompanyEntryDescription(String companyEntryDescription) {
        this.companyEntryDescription = companyEntryDescription;
        return this;
    }

    @ACHField(start = 69, length = 6, name = EFFECTIVE_ENTRY_DATE, inclusion = REQUIRED)
    @DateFormat("yyMMdd")
    public Date getEffectiveEntryDate() {
        return effectiveEntryDate;
    }

    public BatchHeader setEffectiveEntryDate(Date effectiveEntryDate) {
        this.effectiveEntryDate = effectiveEntryDate;
        return this;
    }

    @ACHField(start = 75, length = 3, name = SETTLEMENT_DATE, inclusion = OPTIONAL)
    public Short getSettlementDate() {
        return settlementDate;
    }

    public BatchHeader setSettlementDate(Short settlementDate) {
        this.settlementDate = settlementDate;
        return this;
    }

    @ACHField(start = 78, length = 1, name = ORIGINATOR_STATUS_CODE, inclusion = MANDATORY)
    public String getOriginatorStatusCode() {
        return originatorStatusCode;
    }

    public BatchHeader setOriginatorStatusCode(String originatorStatusCode) {
        this.originatorStatusCode = originatorStatusCode;
        return this;
    }

    @ACHField(start = 79, length = 8, name = ORIGINATOR_DFI_IDENTIFIER, inclusion = MANDATORY)
    public String getOriginatorDFIIdentifier() {
        return originatorDFIIdentifier;
    }

    public BatchHeader setOriginatorDFIIdentifier(String originatorDFIIdentifier) {
        this.originatorDFIIdentifier = originatorDFIIdentifier;
        return this;
    }

    @ACHField(start = 87, length = 7, name = BATCH_NUMBER, inclusion = MANDATORY)
    public Integer getBatchNumber() {
        return batchNumber;
    }

    public BatchHeader setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }
}
