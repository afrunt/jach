package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@ACHRecordType(name = "POS Entry Detail Record")
public class POSEntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private String individualName;
    private String cardTransactionTypeCode;

    @ACHField(start = 39, length = 15, name = NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public POSEntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public POSEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Card Transaction Type Code", inclusion = MANDATORY)
    public String getCardTransactionTypeCode() {
        return cardTransactionTypeCode;
    }

    public POSEntryDetail setCardTransactionTypeCode(String cardTransactionTypeCode) {
        this.cardTransactionTypeCode = cardTransactionTypeCode;
        return this;
    }
}
