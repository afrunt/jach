package com.afrunt.jach.domain.addenda.iat;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import static com.afrunt.jach.ACHValueConstants.*;
import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class FirstIATAddendaRecord extends IATAddendaRecord {
    public static final String FIRST_IAT_ADDENDA_TYPE_CODE = "10";
    public static final String TRANSACTION_TYPE_CODE = "Transaction Type Code";
    public static final String FOREIGN_PAYMENT_AMOUNT = "Foreign Payment Amount";
    public static final String FOREIGN_TRACE_NUMBER = "Foreign Trace Number";
    public static final String RECEIVING_COMPANY_NAME_OR_INDIVIDUAL_NAME = "Receiving Company Name Or Individual Name";

    private String transactionTypeCode;
    private String foreignPaymentAmount;
    private String foreignTraceNumber;
    private String receivingCompanyNameOrIndividualName;

    @Override
    @Values(FIRST_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return FIRST_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 3, name = TRANSACTION_TYPE_CODE, inclusion = REQUIRED, values = {
            "ANN", "BUS", "DEP", "LOA", "MIS", "MOR", "PEN", "RLS", "SAL", "TAX"
    })
    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public FirstIATAddendaRecord setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
        return this;
    }

    @ACHField(start = 6, length = 18, name = FOREIGN_PAYMENT_AMOUNT, inclusion = REQUIRED,
            values = {FILLED_WITH_ZEROS, FILLED_WITH_SPACES, DOLLAR_AMOUNT})
    public String getForeignPaymentAmount() {
        return foreignPaymentAmount;
    }

    public FirstIATAddendaRecord setForeignPaymentAmount(String foreignPaymentAmount) {
        this.foreignPaymentAmount = foreignPaymentAmount;
        return this;
    }

    @ACHField(start = 24, length = 22, name = FOREIGN_TRACE_NUMBER, inclusion = OPTIONAL,
            values = {FILLED_WITH_ZEROS, FILLED_WITH_SPACES})
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
