package com.afrunt.jach.domain.addenda;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.AddendaRecord;

import java.math.BigInteger;

import static com.afrunt.jach.annotation.InclusionRequirement.BLANK;
import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;
import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * @author Andrii Frunt
 */
public abstract class BaseCORAddendaRecord extends AddendaRecord {
    public static final String ORIGINAL_ENTRY_TRACE_NUMBER = "Original Entry Trace Number";
    public static final String ORIGINAL_RDFI_IDENTIFICATION = "Original RDFI Identification";
    public static final String CORRECTED_DATA = "Corrected Data";
    public static final String COR_ADDENDA_TYPE_CODE = "98";

    private BigInteger originalEntryTraceNumber;
    private Long originalRDFIID;
    private String correctedData;
    private BigInteger traceNumber;

    @Override
    @Values(COR_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return COR_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 6, length = 15, inclusion = MANDATORY, name = ORIGINAL_ENTRY_TRACE_NUMBER)
    public BigInteger getOriginalEntryTraceNumber() {
        return originalEntryTraceNumber;
    }

    public BaseCORAddendaRecord setOriginalEntryTraceNumber(BigInteger originalEntryTraceNumber) {
        this.originalEntryTraceNumber = originalEntryTraceNumber;
        return this;
    }

    @ACHField(start = 21, length = 6, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(6);
    }

    @ACHField(start = 27, length = 8, inclusion = REQUIRED, name = ORIGINAL_RDFI_IDENTIFICATION)
    public Long getOriginalRDFIID() {
        return originalRDFIID;
    }

    public BaseCORAddendaRecord setOriginalRDFIID(Long originalRDFIID) {
        this.originalRDFIID = originalRDFIID;
        return this;
    }

    @ACHField(start = 35, length = 29, inclusion = MANDATORY, name = CORRECTED_DATA)
    public String getCorrectedData() {
        return correctedData;
    }

    public BaseCORAddendaRecord setCorrectedData(String correctedData) {
        this.correctedData = correctedData;
        return this;
    }

    @ACHField(start = 79, length = 15, inclusion = MANDATORY, name = TRACE_NUMBER)
    public BigInteger getTraceNumber() {
        return traceNumber;
    }


    public BaseCORAddendaRecord setTraceNumber(BigInteger traceNumber) {
        this.traceNumber = traceNumber;
        return this;
    }
}
