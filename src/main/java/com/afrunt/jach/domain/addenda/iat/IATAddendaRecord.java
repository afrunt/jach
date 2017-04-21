package com.afrunt.jach.domain.addenda.iat;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.domain.AddendaRecord;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
public abstract class IATAddendaRecord extends AddendaRecord {
    private Long entryDetailSequenceNumber;

    @ACHField(start = 87, length = 7, name = ENTRY_DETAIL_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Long getEntryDetailSequenceNumber() {
        return entryDetailSequenceNumber;
    }

    public AddendaRecord setEntryDetailSequenceNumber(Long entryDetailSequenceNumber) {
        this.entryDetailSequenceNumber = entryDetailSequenceNumber;
        return this;
    }
}
