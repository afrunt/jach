package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.domain.NonIATEntryDetail;
import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

@ACHRecordType(name = "COR Entry Detail Record")
public class COREntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private String receivingCompanyName;
    private String discretionaryData;

    @ACHField(start = 39, length = 15, name = NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public COREntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, inclusion = REQUIRED, name = NonIATEntryDetail.RECEIVING_COMPANY_NAME)
    public String getReceivingCompanyName() {
        return receivingCompanyName;
    }

    public COREntryDetail setReceivingCompanyName(String receivingCompanyName) {
        this.receivingCompanyName = receivingCompanyName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public COREntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
