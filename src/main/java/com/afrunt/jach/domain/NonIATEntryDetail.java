package com.afrunt.jach.domain;

import com.afrunt.jach.annotation.ACHField;

import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * @author Andrii Frunt
 */
public abstract class NonIATEntryDetail extends EntryDetail {
    public static final String DFI_ACCOUNT_NUMBER = "DFI Account Number";
    public static final String CHECK_SERIAL_NUMBER = "Check Serial Number";
    public static final String IDENTIFICATION_NUMBER = "Identification Number";
    public static final String RECEIVING_COMPANY_NAME = "Receiving Company Name";

    private String dfiAccountNumber;

    @ACHField(start = 12, length = 29, inclusion = REQUIRED, name = DFI_ACCOUNT_NUMBER)
    public String getDfiAccountNumber() {
        return this.dfiAccountNumber;
    }

    public NonIATEntryDetail setDfiAccountNumber(String dfiAccountNumber) {
        this.dfiAccountNumber = dfiAccountNumber;
        return this;
    }
}
