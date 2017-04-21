package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class CTXEntryDetail extends NonIATEntryDetail {
    private String identificationNumber;
    private Short numberOfAddendaRecords;
    private String discretionaryData;
    private String receivingCompanyName;

    @Override
    @Values("1")
    public Short getAddendaRecordIndicator() {
        return 1;
    }

    @ACHField(start = 39, length = 15, name = NonIATEntryDetail.IDENTIFICATION_NUMBER)
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public CTXEntryDetail setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @ACHField(start = 54, length = 4, inclusion = MANDATORY, name = "Number Of Addenda Records")
    public Short getNumberOfAddendaRecords() {
        return numberOfAddendaRecords;
    }

    public CTXEntryDetail setNumberOfAddendaRecords(Short numberOfAddendaRecords) {
        this.numberOfAddendaRecords = numberOfAddendaRecords;
        return this;
    }

    @ACHField(start = 58, length = 16, inclusion = REQUIRED, name = NonIATEntryDetail.RECEIVING_COMPANY_NAME)
    public String getReceivingCompanyName() {
        return receivingCompanyName;
    }

    public CTXEntryDetail setReceivingCompanyName(String receivingCompanyName) {
        this.receivingCompanyName = receivingCompanyName;
        return this;
    }

    @ACHField(start = 74, length = 2, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(2);
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public CTXEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
