package com.afrunt.jach.document;

import com.afrunt.jach.domain.AddendaRecord;
import com.afrunt.jach.domain.EntryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Frunt
 */
public class ACHBatchDetail {
    private EntryDetail detailRecord;
    private List<AddendaRecord> addendaRecords = new ArrayList<>();

    public ACHBatchDetail addAddendaRecord(AddendaRecord addendaRecord) {
        addendaRecords = addendaRecords == null ? new ArrayList<>() : addendaRecords;
        addendaRecords.add(addendaRecord);
        return this;
    }

    public EntryDetail getDetailRecord() {
        return detailRecord;
    }

    public ACHBatchDetail setDetailRecord(EntryDetail detailRecord) {
        this.detailRecord = detailRecord;
        return this;
    }

    public List<AddendaRecord> getAddendaRecords() {
        return addendaRecords;
    }

    public ACHBatchDetail setAddendaRecords(List<AddendaRecord> addendaRecords) {
        this.addendaRecords = addendaRecords;
        return this;
    }
}
