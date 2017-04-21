package com.afrunt.jach.document;

import com.afrunt.jach.domain.BatchControl;
import com.afrunt.jach.domain.BatchHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrii Frunt
 */
public class ACHBatch {
    private BatchHeader batchHeader;
    private List<ACHBatchDetail> details = new ArrayList<>();
    private BatchControl batchControl;

    public String getBatchType() {
        return Optional.ofNullable(batchHeader)
                .map(BatchHeader::getStandardEntryClassCode)
                .orElse(null);
    }

    public ACHBatch addDetail(ACHBatchDetail detail) {
        details = details == null ? new ArrayList<>() : details;
        details.add(detail);
        return this;
    }

    public BatchHeader getBatchHeader() {
        return batchHeader;
    }

    public ACHBatch setBatchHeader(BatchHeader batchHeader) {
        this.batchHeader = batchHeader;
        return this;
    }

    public List<ACHBatchDetail> getDetails() {
        return details;
    }

    public ACHBatch setDetails(List<ACHBatchDetail> details) {
        this.details = details;
        return this;
    }

    public BatchControl getBatchControl() {
        return batchControl;
    }

    public ACHBatch setBatchControl(BatchControl batchControl) {
        this.batchControl = batchControl;
        return this;
    }
}
