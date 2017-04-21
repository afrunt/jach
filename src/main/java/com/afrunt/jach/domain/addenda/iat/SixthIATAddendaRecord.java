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
public class SixthIATAddendaRecord extends IATAddendaRecord {

    public static final String SIXTH_IAT_ADDENDA_TYPE_CODE = "15";
    public static final String RECEIVER_IDENTIFICATION_NUMBER = "Receiver Identification Number";
    public static final String RECEIVER_STREET_ADDRESS = "Receiver Street Address";
    private String receiverIdentificationNumber;
    private String receiverStreetAddress;

    @Override
    @Values(SIXTH_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SIXTH_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 15, name = RECEIVER_IDENTIFICATION_NUMBER)
    public String getReceiverIdentificationNumber() {
        return receiverIdentificationNumber;
    }

    public SixthIATAddendaRecord setReceiverIdentificationNumber(String receiverIdentificationNumber) {
        this.receiverIdentificationNumber = receiverIdentificationNumber;
        return this;
    }

    @ACHField(start = 18, length = 35, name = RECEIVER_STREET_ADDRESS, inclusion = MANDATORY)
    public String getReceiverStreetAddress() {
        return receiverStreetAddress;
    }

    public SixthIATAddendaRecord setReceiverStreetAddress(String receiverStreetAddress) {
        this.receiverStreetAddress = receiverStreetAddress;
        return this;
    }

    @ACHField(start = 53, length = 34, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(34);
    }

}
