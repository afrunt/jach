package com.afrunt.jach.domain.addenda;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.AddendaRecord;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.OPTIONAL;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class GeneralAddendaRecord extends AddendaRecord {
    private static final String CCD_ADDENDA_TYPE_CODE = "05";
    private static final String PAYMENT_RELATED_INFORMATION = "Payment Related Information";
    private static final String ADDENDA_SEQUENCE_NUMBER = "Addenda Sequence Number";

    private String paymentRelatedInformation;
    private Integer addendaSequenceNumber;

    private Long entryDetailSequenceNumber;

    @Override
    @Values(CCD_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return CCD_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 80, name = PAYMENT_RELATED_INFORMATION, inclusion = OPTIONAL)
    public String getPaymentRelatedInformation() {
        return paymentRelatedInformation;
    }

    public GeneralAddendaRecord setPaymentRelatedInformation(String paymentRelatedInformation) {
        this.paymentRelatedInformation = paymentRelatedInformation;
        return this;
    }

    @ACHField(start = 83, length = 4, name = ADDENDA_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Integer getAddendaSequenceNumber() {
        return addendaSequenceNumber;
    }

    public GeneralAddendaRecord setAddendaSequenceNumber(Integer addendaSequenceNumber) {
        this.addendaSequenceNumber = addendaSequenceNumber;
        return this;
    }

    @ACHField(start = 87, length = 7, name = ENTRY_DETAIL_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Long getEntryDetailSequenceNumber() {
        return entryDetailSequenceNumber;
    }

    public AddendaRecord setEntryDetailSequenceNumber(Long entryDetailSequenceNumber) {
        this.entryDetailSequenceNumber = entryDetailSequenceNumber;
        return this;
    }
}
