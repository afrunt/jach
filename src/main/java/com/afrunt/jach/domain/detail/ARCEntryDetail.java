package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class ARCEntryDetail extends NonIATEntryDetail {
    private String checkSerialNumber;
    private String individualName;
    private String discretionaryData;

    @ACHField(start = 39, length = 15, inclusion = MANDATORY, name = NonIATEntryDetail.CHECK_SERIAL_NUMBER)
    public String getCheckSerialNumber() {
        return checkSerialNumber;
    }

    public ARCEntryDetail setCheckSerialNumber(String checkSerialNumber) {
        this.checkSerialNumber = checkSerialNumber;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public ARCEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public ARCEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
