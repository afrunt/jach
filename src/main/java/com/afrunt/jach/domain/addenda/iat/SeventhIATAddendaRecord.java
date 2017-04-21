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
public class SeventhIATAddendaRecord extends IATAddendaRecord {

    public static final String SEVENTH_IAT_ADDENDA_TYPE_CODE = "16";
    public static final String RECEIVER_CITY_STATE_PROVINCE = "Receiver City & State/Province";
    public static final String RECEIVER_COUNTRY_POSTAL_CODE = "Receiver Country & Postal Code";
    private String receiverCityAndStateProvince;
    private String receiverCountryAndPostalCode;

    @Override
    @Values(SEVENTH_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return SEVENTH_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, name = RECEIVER_CITY_STATE_PROVINCE, inclusion = MANDATORY)
    public String getReceiverCityAndStateProvince() {
        return receiverCityAndStateProvince;
    }


    public SeventhIATAddendaRecord setReceiverCityAndStateProvince(String receiverCityAndStateProvince) {
        this.receiverCityAndStateProvince = receiverCityAndStateProvince;
        return this;
    }

    @ACHField(start = 38, length = 35, name = RECEIVER_COUNTRY_POSTAL_CODE, inclusion = MANDATORY)
    public String getReceiverCountryAndPostalCode() {
        return receiverCountryAndPostalCode;
    }

    public SeventhIATAddendaRecord setReceiverCountryAndPostalCode(String receiverCountryAndPostalCode) {
        this.receiverCountryAndPostalCode = receiverCountryAndPostalCode;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }
}
