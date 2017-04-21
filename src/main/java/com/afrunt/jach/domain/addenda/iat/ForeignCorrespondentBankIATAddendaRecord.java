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
public class ForeignCorrespondentBankIATAddendaRecord extends IATAddendaRecord {
    public static final String FOREIGN_CORRESPONDENT_BANK_ADDENDA_TYPE_CODE = "18";
    public static final String NATIONAL_CLEARING_SYSTEM = "01";
    public static final String BIC = "02";
    public static final String IBAN = "03";
    public static final String FOREIGN_CORRESPONDENT_BANK_NAME = "Foreign Correspondent Bank Name";
    public static final String FOREIGN_CORRESPONDENT_BANK_IDENTIFICATION_NUMBER_QUALIFIER = "Foreign Correspondent Bank Identification Number Qualifier";
    public static final String FOREIGN_CORRESPONDENT_BANK_IDENTIFICATION_NUMBER = "Foreign Correspondent Bank Identification Number";
    public static final String FOREIGN_CORRESPONDENT_BANK_BRANCH_COUNTRY_CODE = "Foreign Correspondent Bank Branch Country Code";
    public static final String ADDENDA_SEQUENCE_NUMBER = "Addenda Sequence Number";

    private String bankName;
    private String bankIDNumberQualifier;
    private String bankIDNumber;
    private String bankBranchCountryCode;

    private Integer addendaSequenceNumber;

    @Override
    @Values(FOREIGN_CORRESPONDENT_BANK_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return FOREIGN_CORRESPONDENT_BANK_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 35, inclusion = MANDATORY, name = FOREIGN_CORRESPONDENT_BANK_NAME)
    public String getBankName() {
        return bankName;
    }

    public ForeignCorrespondentBankIATAddendaRecord setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    @ACHField(start = 38, length = 2, inclusion = MANDATORY, values = {NATIONAL_CLEARING_SYSTEM, BIC, IBAN},
            name = FOREIGN_CORRESPONDENT_BANK_IDENTIFICATION_NUMBER_QUALIFIER)
    public String getBankIDNumberQualifier() {
        return bankIDNumberQualifier;
    }

    public ForeignCorrespondentBankIATAddendaRecord setBankIDNumberQualifier(String bankIDNumberQualifier) {
        this.bankIDNumberQualifier = bankIDNumberQualifier;
        return this;
    }

    @ACHField(start = 40, length = 34, inclusion = MANDATORY,
            name = FOREIGN_CORRESPONDENT_BANK_IDENTIFICATION_NUMBER)
    public String getBankIDNumber() {
        return bankIDNumber;
    }

    public ForeignCorrespondentBankIATAddendaRecord setBankIDNumber(String bankIDNumber) {
        this.bankIDNumber = bankIDNumber;
        return this;
    }

    @ACHField(start = 74, length = 3, inclusion = MANDATORY,
            name = FOREIGN_CORRESPONDENT_BANK_BRANCH_COUNTRY_CODE)
    public String getBankBranchCountryCode() {
        return bankBranchCountryCode;
    }

    public ForeignCorrespondentBankIATAddendaRecord setBankBranchCountryCode(String bankBranchCountryCode) {
        this.bankBranchCountryCode = bankBranchCountryCode;
        return this;
    }

    @ACHField(start = 77, length = 6, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(6);
    }

    @ACHField(start = 83, length = 4, inclusion = MANDATORY, name = ADDENDA_SEQUENCE_NUMBER)
    public Integer getAddendaSequenceNumber() {
        return addendaSequenceNumber;
    }

    public ForeignCorrespondentBankIATAddendaRecord setAddendaSequenceNumber(Integer addendaSequenceNumber) {
        this.addendaSequenceNumber = addendaSequenceNumber;
        return this;
    }
}
