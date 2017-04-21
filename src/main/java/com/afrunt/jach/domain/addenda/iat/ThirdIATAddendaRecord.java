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
public class ThirdIATAddendaRecord extends IATAddendaRecord {
    public static final String THIRD_IAT_ADDENDA_TYPE_CODE = "12";
    public static final String ORIGINATOR_S_CITY_STATE_PROVINCE = "Originator's City & State/Province";
    public static final String ORIGINATOR_S_COUNTRY_POSTAL_CODE = "Originator's Country & Postal Code";

    private String originatorCityAndStateProvince;
    private String originatorCountryAndPostalCode;

    @Override
    @Values(THIRD_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return THIRD_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, name = ORIGINATOR_S_CITY_STATE_PROVINCE, inclusion = MANDATORY)
    public String getOriginatorCityAndStateProvince() {
        return originatorCityAndStateProvince;
    }

    public ThirdIATAddendaRecord setOriginatorCityAndStateProvince(String originatorName) {
        this.originatorCityAndStateProvince = originatorName;
        return this;
    }

    @ACHField(start = 38, length = 35, name = ORIGINATOR_S_COUNTRY_POSTAL_CODE, inclusion = MANDATORY)
    public String getOriginatorCountryAndPostalCode() {
        return originatorCountryAndPostalCode;
    }

    public ThirdIATAddendaRecord setOriginatorCountryAndPostalCode(String originatorStreetAddress) {
        this.originatorCountryAndPostalCode = originatorStreetAddress;
        return this;
    }

    @ACHField(start = 73, length = 14, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(14);
    }

}
