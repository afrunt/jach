package com.afrunt.jach.domain.addenda.iat;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class SecondIATAddendaRecord extends IATAddendaRecord {
    public static final String SECOND_IAT_ADDENDA_TYPE_CODE = "11";
    public static final String ORIGINATOR_S_NAME = "Originator's Name";
    public static final String ORIGINATOR_S_PHYSICAL_ADDRESS = "Originator's physical address";

    private String originatorName;
    private String originatorStreetAddress;

    @Override
    @Values(SECOND_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SECOND_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, name = ORIGINATOR_S_NAME, inclusion = MANDATORY)
    public String getOriginatorName() {
        return originatorName;
    }

    public SecondIATAddendaRecord setOriginatorName(String originatorName) {
        this.originatorName = originatorName;
        return this;
    }

    @ACHField(start = 38, length = 35, name = ORIGINATOR_S_PHYSICAL_ADDRESS, inclusion = MANDATORY)
    public String getOriginatorStreetAddress() {
        return originatorStreetAddress;
    }

    public SecondIATAddendaRecord setOriginatorStreetAddress(String originatorStreetAddress) {
        this.originatorStreetAddress = originatorStreetAddress;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }
}
