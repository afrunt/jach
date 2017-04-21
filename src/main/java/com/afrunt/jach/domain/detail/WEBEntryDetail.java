package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class WEBEntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private String individualName;
    private String paymentTypeCode;

    @ACHField(start = 39, length = 15, inclusion = MANDATORY, name = NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public WEBEntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public WEBEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, inclusion = REQUIRED, name = "Payment Type Code")
    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public WEBEntryDetail setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
        return this;
    }
}
