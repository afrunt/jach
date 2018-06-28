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
import java.util.Date;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * Point of Sale addenda record
 *
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "POS Addenda Record")
public class POSAddendaRecord extends AddendaRecord {
    public static final String POS_ADDENDA_TYPE_CODE = "02";
    public static final String REFERENCE_INFORMATION_1 = "Reference Information #1";
    public static final String REFERENCE_INFORMATION_2 = "Reference Information #2";
    public static final String TERMINAL_ID_CODE = "Terminal ID Code";
    public static final String TRANSACTION_SERIAL_NUMBER = "Transaction Serial Number";
    public static final String TRANSACTION_DATE = "Transaction Date";
    public static final String AUTHORIZATION_CODE_OR_CARD_EXPIRATION = "Authorization Code or Card Expiration";
    public static final String TERMINAL_LOCATION = "Terminal Location";
    public static final String TERMINAL_CITY = "Terminal City";
    public static final String TERMINAL_STATE = "Terminal State";
    public static final String TRACE_NUMBER = "Trace Number";

    private String referenceInformation1;
    private String referenceInformation2;
    private String terminalIDCode;
    private String transactionSerialNumber;
    private Date transactionDate;
    private String authorizationCodeOrCardExpiration;
    private String terminalLocation;
    private String terminalCity;
    private String terminalState;
    private BigInteger traceNumber;

    @Override
    @Values(POS_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return POS_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 7, name = REFERENCE_INFORMATION_1)
    public String getReferenceInformation1() {
        return referenceInformation1;
    }

    public POSAddendaRecord setReferenceInformation1(String referenceInformation1) {
        this.referenceInformation1 = referenceInformation1;
        return this;
    }

    @ACHField(start = 10, length = 7, name = REFERENCE_INFORMATION_2)
    public String getReferenceInformation2() {
        return referenceInformation2;
    }

    public POSAddendaRecord setReferenceInformation2(String referenceInformation2) {
        this.referenceInformation2 = referenceInformation2;
        return this;
    }

    @ACHField(start = 13, length = 6, inclusion = REQUIRED, name = TERMINAL_ID_CODE)
    public String getTerminalIDCode() {
        return terminalIDCode;
    }

    public POSAddendaRecord setTerminalIDCode(String terminalIDCode) {
        this.terminalIDCode = terminalIDCode;
        return this;
    }

    @ACHField(start = 19, length = 6, inclusion = REQUIRED, name = TRANSACTION_SERIAL_NUMBER)
    public String getTransactionSerialNumber() {
        return transactionSerialNumber;
    }

    public POSAddendaRecord setTransactionSerialNumber(String transactionSerialNumber) {
        this.transactionSerialNumber = transactionSerialNumber;
        return this;
    }

    @ACHField(start = 25, length = 4, inclusion = REQUIRED, name = TRANSACTION_DATE, dateFormat = "MMdd")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public POSAddendaRecord setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    @ACHField(start = 29, length = 6, name = AUTHORIZATION_CODE_OR_CARD_EXPIRATION)
    public String getAuthorizationCodeOrCardExpiration() {
        return authorizationCodeOrCardExpiration;
    }

    public POSAddendaRecord setAuthorizationCodeOrCardExpiration(String authorizationCodeOrCardExpiration) {
        this.authorizationCodeOrCardExpiration = authorizationCodeOrCardExpiration;
        return this;
    }

    @ACHField(start = 35, length = 27, inclusion = REQUIRED, name = TERMINAL_LOCATION)
    public String getTerminalLocation() {
        return terminalLocation;
    }

    public POSAddendaRecord setTerminalLocation(String terminalLocation) {
        this.terminalLocation = terminalLocation;
        return this;
    }

    @ACHField(start = 62, length = 15, inclusion = REQUIRED, name = TERMINAL_CITY)
    public String getTerminalCity() {
        return terminalCity;
    }

    public POSAddendaRecord setTerminalCity(String terminalCity) {
        this.terminalCity = terminalCity;
        return this;
    }

    @ACHField(start = 77, length = 2, inclusion = REQUIRED, name = TERMINAL_STATE)
    public String getTerminalState() {
        return terminalState;
    }

    public POSAddendaRecord setTerminalState(String terminalState) {
        this.terminalState = terminalState;
        return this;
    }

    @ACHField(start = 79, length = 15, inclusion = MANDATORY, name = TRACE_NUMBER)
    public BigInteger getTraceNumber() {
        return traceNumber;
    }


    public POSAddendaRecord setTraceNumber(BigInteger traceNumber) {
        this.traceNumber = traceNumber;
        return this;
    }
}
