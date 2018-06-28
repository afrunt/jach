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

import java.math.BigDecimal;

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * The First Addenda Record identifies the Receiver of the transaction and the dollar amount of the
 * payment.
 *
 * @author Andrii Frunt
 */
@SuppressWarnings({"WeakerAccess", "JavaDoc"})
@ACHRecordType(name = "First IAT Addenda Record")
public class FirstIATAddendaRecord extends IATAddendaRecord {
    public static final String FIRST_IAT_ADDENDA_TYPE_CODE = "10";
    public static final String TRANSACTION_TYPE_CODE = "Transaction Type Code";
    public static final String FOREIGN_PAYMENT_AMOUNT = "Foreign Payment Amount";
    public static final String FOREIGN_TRACE_NUMBER = "Foreign Trace Number";
    public static final String RECEIVING_COMPANY_NAME_OR_INDIVIDUAL_NAME = "Receiving Company Name Or Individual Name";

    private String transactionTypeCode;
    private BigDecimal foreignPaymentAmount;
    private String foreignTraceNumber;
    private String receivingCompanyNameOrIndividualName;

    /**
     * First Addenda Record for IAT
     *
     * @return "10"
     */
    @Override
    @Values(FIRST_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return FIRST_IAT_ADDENDA_TYPE_CODE;
    }

    /**
     * Describes the type of payment:
     * <p>
     * ANN = Annuity
     * BUS = Business/Commercial DEP = Deposit
     * LOA = Loan
     * MIS = Miscellaneous
     * MOR = Mortgage
     * PEN = Pension
     * RLS = Rent/Lease
     * REM = Remittance2
     * SAL = Salary/Payroll
     * TAX = Tax
     * TEL = Telephone-Initiated Transaction
     * WEB = Internet-Initiated Transaction
     * ARC = Accounts Receivable Entry
     * BOC = Back Office Conversion Entry
     * POP = Point of Purchase Entry
     * RCK = Re-presented Check Entry
     *
     * @return type of payment
     */
    @ACHField(start = 3, length = 3, name = TRANSACTION_TYPE_CODE, inclusion = REQUIRED, values = {
            "ANN", "BUS", "DEP", "LOA", "MIS", "MOR", "PEN", "RLS", "SAL", "TAX", "TEL", "WEB", "ARC", "BOC", "POP", "RCK"
    })
    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public FirstIATAddendaRecord setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
        return this;
    }

    /**
     * For inbound IAT payments this field should contain the USD amount or may be blank.
     *
     * @return
     */
    @ACHField(start = 6, length = 18, name = FOREIGN_PAYMENT_AMOUNT, inclusion = REQUIRED)
    public BigDecimal getForeignPaymentAmount() {
        return foreignPaymentAmount;
    }

    public FirstIATAddendaRecord setForeignPaymentAmount(BigDecimal foreignPaymentAmount) {
        this.foreignPaymentAmount = foreignPaymentAmount;
        return this;
    }

    @ACHField(start = 24, length = 22, name = FOREIGN_TRACE_NUMBER, inclusion = OPTIONAL)
    public String getForeignTraceNumber() {
        return foreignTraceNumber;
    }

    public FirstIATAddendaRecord setForeignTraceNumber(String foreignTraceNumber) {
        this.foreignTraceNumber = foreignTraceNumber;
        return this;
    }

    @ACHField(start = 46, length = 35, name = RECEIVING_COMPANY_NAME_OR_INDIVIDUAL_NAME, inclusion = MANDATORY)
    public String getReceivingCompanyNameOrIndividualName() {
        return receivingCompanyNameOrIndividualName;
    }

    public FirstIATAddendaRecord setReceivingCompanyNameOrIndividualName(String receivingCompanyNameOrIndividualName) {
        this.receivingCompanyNameOrIndividualName = receivingCompanyNameOrIndividualName;
        return this;
    }

    @ACHField(start = 81, length = 6, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(6);
    }

}
