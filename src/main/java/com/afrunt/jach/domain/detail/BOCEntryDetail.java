package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Inclusion;
import com.afrunt.jach.annotation.InclusionRequirement;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class BOCEntryDetail extends NonIATEntryDetail {

    private String checkSerialNumber;
    private String individualName;
    private String discretionaryData;

    @Override
    @Inclusion(InclusionRequirement.OPTIONAL)
    public Short getAddendaRecordIndicator() {
        return super.getAddendaRecordIndicator();
    }

    @ACHField(start = 39, length = 15, inclusion = MANDATORY, name= NonIATEntryDetail.CHECK_SERIAL_NUMBER)
    public String getCheckSerialNumber() {
        return checkSerialNumber;
    }

    public BOCEntryDetail setCheckSerialNumber(String checkSerialNumber) {
        this.checkSerialNumber = checkSerialNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public BOCEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public BOCEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
