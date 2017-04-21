package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.domain.NonIATEntryDetail;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class PPDEntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private String individualName;
    private String discretionaryData;

    @ACHField(start = 39, length = 15, name= NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public PPDEntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public PPDEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public PPDEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }

}
