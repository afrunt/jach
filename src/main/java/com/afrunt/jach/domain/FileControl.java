package com.afrunt.jach.domain;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import java.math.BigDecimal;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.domain.RecordTypes.Constants.FILE_CONTROL_RECORD_TYPE_CODE;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class FileControl extends ACHRecord {
    public static final String BATCH_COUNT = "Batch Count";
    public static final String BLOCK_COUNT = "Block Count";
    public static final String ENTRY_ADDENDA_COUNT = "Entry Addenda Count";
    public static final String ENTRY_HASH = "Entry Hash";
    public static final String TOTAL_DEBITS = "Total Debits";
    public static final String TOTAL_CREDITS = "Total Credits";
    private Integer batchCount;
    private Integer blockCount;
    private Integer entryAddendaCount;
    private Long entryHashTotals;
    private BigDecimal totalDebits;
    private BigDecimal totalCredits;

    @Override
    @Values(FILE_CONTROL_RECORD_TYPE_CODE)
    public String getRecordTypeCode() {
        return FILE_CONTROL_RECORD_TYPE_CODE;
    }


    @ACHField(start = 1, length = 6, name = BATCH_COUNT, inclusion = MANDATORY)
    public Integer getBatchCount() {
        return this.batchCount;
    }

    public FileControl setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
        return this;
    }

    @ACHField(start = 7, length = 6, name = BLOCK_COUNT, inclusion = MANDATORY)
    public Integer getBlockCount() {
        return this.blockCount;
    }

    public FileControl setBlockCount(Integer blockCount) {
        this.blockCount = blockCount;
        return this;
    }

    @ACHField(start = 13, length = 8, name = ENTRY_ADDENDA_COUNT, inclusion = MANDATORY)
    public Integer getEntryAddendaCount() {
        return this.entryAddendaCount;
    }

    public FileControl setEntryAddendaCount(Integer entryAddendaCount) {
        this.entryAddendaCount = entryAddendaCount;
        return this;
    }

    @ACHField(start = 21, length = 10, name = ENTRY_HASH, inclusion = MANDATORY)
    public Long getEntryHashTotals() {
        return this.entryHashTotals;
    }

    public FileControl setEntryHashTotals(Long entryHashTotals) {
        this.entryHashTotals = entryHashTotals;
        return this;
    }

    @ACHField(start = 31, length = 12, name = TOTAL_DEBITS, inclusion = MANDATORY)
    public BigDecimal getTotalDebits() {
        return this.totalDebits;
    }

    public FileControl setTotalDebits(BigDecimal totalDebits) {
        this.totalDebits = totalDebits;
        return this;
    }

    @ACHField(start = 43, length = 12, name = TOTAL_CREDITS, inclusion = MANDATORY)
    public BigDecimal getTotalCredits() {
        return this.totalCredits;
    }

    public FileControl setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
        return this;
    }

    @ACHField(start = 55, length = 39, name = RESERVED, inclusion = BLANK)
    public String getReserved() {
        return reserved(39);
    }
}
