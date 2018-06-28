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

import java.math.BigInteger;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "Return Addenda Record")
public class ReturnAddendaRecord extends AddendaRecord {
    public static final String RETURN_ADDENDA_TYPE_CODE = "99";
    public static final String ADDENDA_INFORMATION = "Addenda Information";
    public static final String ORIGINAL_RECEIVING_DFI_IDENTIFICATION = "Original Receiving DFI Identification";
    public static final String DATE_OF_DEATH = "Date Of Death";
    public static final String ORIGINAL_ENTRY_TRACE_NUMBER = "Original Entry Trace Number";
    public static final String RETURN_REASON_CODE = "Return Reason Code";

    private String returnReasonCode;
    private BigInteger originalEntryTraceNumber;
    private String dateOfDeath;
    private String originalReceivingDFIID;
    private String addendaInformation;
    private BigInteger traceNumber;

    @Override
    @Values(RETURN_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return RETURN_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 3, inclusion = MANDATORY, name = RETURN_REASON_CODE)
    public String getReturnReasonCode() {
        return returnReasonCode;
    }

    public ReturnAddendaRecord setReturnReasonCode(String returnReasonCode) {
        this.returnReasonCode = returnReasonCode;
        return this;
    }

    @ACHField(start = 6, length = 15, inclusion = MANDATORY, name = ORIGINAL_ENTRY_TRACE_NUMBER)
    public BigInteger getOriginalEntryTraceNumber() {
        return originalEntryTraceNumber;
    }

    public ReturnAddendaRecord setOriginalEntryTraceNumber(BigInteger originalEntryTraceNumber) {
        this.originalEntryTraceNumber = originalEntryTraceNumber;
        return this;
    }

    @ACHField(start = 21, length = 6, name = DATE_OF_DEATH)
    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public ReturnAddendaRecord setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
        return this;
    }

    @ACHField(start = 27, length = 8, inclusion = REQUIRED, name = ORIGINAL_RECEIVING_DFI_IDENTIFICATION)
    public String getOriginalReceivingDFIID() {
        return originalReceivingDFIID;
    }

    public ReturnAddendaRecord setOriginalReceivingDFIID(String originalReceivingDFIID) {
        this.originalReceivingDFIID = originalReceivingDFIID;
        return this;
    }

    @ACHField(start = 35, length = 43, name = ADDENDA_INFORMATION)
    public String getAddendaInformation() {
        return addendaInformation;
    }

    public ReturnAddendaRecord setAddendaInformation(String addendaInformation) {
        this.addendaInformation = addendaInformation;
        return this;
    }

    @ACHField(start = 79, length = 15, inclusion = MANDATORY, name = AddendaRecord.TRACE_NUMBER)
    public BigInteger getTraceNumber() {
        return traceNumber;
    }


    public ReturnAddendaRecord setTraceNumber(BigInteger traceNumber) {
        this.traceNumber = traceNumber;
        return this;
    }
}
