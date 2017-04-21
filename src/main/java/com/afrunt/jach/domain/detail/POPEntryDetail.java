package com.afrunt.jach.domain.detail;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Inclusion;
import com.afrunt.jach.domain.NonIATEntryDetail;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.OPTIONAL;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class POPEntryDetail extends NonIATEntryDetail {

    private String checkSerialNumber;
    private String individualName;
    private String discretionaryData;
    private String terminalCity;
    private String terminalState;

    @Override
    @Inclusion(OPTIONAL)
    public Short getAddendaRecordIndicator() {
        return super.getAddendaRecordIndicator();
    }

    @ACHField(start = 39, length = 9, inclusion = MANDATORY, name = NonIATEntryDetail.CHECK_SERIAL_NUMBER)
    public String getCheckSerialNumber() {
        return checkSerialNumber;
    }

    public POPEntryDetail setCheckSerialNumber(String checkSerialNumber) {
        this.checkSerialNumber = checkSerialNumber;
        return this;
    }

    @ACHField(start = 48, length = 4, inclusion = MANDATORY, name = "Terminal City")
    public String getTerminalCity() {
        return terminalCity;
    }

    public POPEntryDetail setTerminalCity(String terminalCity) {
        this.terminalCity = terminalCity;
        return this;
    }

    @ACHField(start = 52, length = 2, inclusion = MANDATORY, name = "Terminal State")
    public String getTerminalState() {
        return terminalState;
    }

    public POPEntryDetail setTerminalState(String terminalState) {
        this.terminalState = terminalState;
        return this;
    }

    @ACHField(start = 54, length = 22, name = "Individual Name")
    public String getIndividualName() {
        return individualName;
    }

    public POPEntryDetail setIndividualName(String individualName) {
        this.individualName = individualName;
        return this;
    }

    @ACHField(start = 76, length = 2, name = "Discretionary Data")
    public String getDiscretionaryData() {
        return discretionaryData;
    }

    public POPEntryDetail setDiscretionaryData(String discretionaryData) {
        this.discretionaryData = discretionaryData;
        return this;
    }
}
