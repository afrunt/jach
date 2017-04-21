package com.afrunt.jach.domain.addenda;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.AddendaRecord;

import java.math.BigInteger;

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class CORAddendaRecord extends AddendaRecord {
    public static final String COR_ADDENDA_TYPE_CODE = "98";
    public static final String CORRECTED_DATA = "Corrected Data";
    public static final String ORIGINAL_RDFI_IDENTIFICATION = "Original RDFI Identification";
    public static final String ORIGINAL_ENTRY_TRACE_NUMBER = "Original Entry Trace Number";
    public static final String CHANGE_CODE = "Change Code";

    private String changeCode;
    private BigInteger originalEntryTraceNumber;
    private Long originalRDFIID;
    private String correctedData;
    private BigInteger traceNumber;

    @Override
    @Values(COR_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return COR_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 2, inclusion = MANDATORY, name = CHANGE_CODE)
    public String getChangeCode() {
        return changeCode;
    }

    public CORAddendaRecord setChangeCode(String changeCode) {
        this.changeCode = changeCode;
        return this;
    }

    @ACHField(start = 6, length = 14, inclusion = MANDATORY, name = ORIGINAL_ENTRY_TRACE_NUMBER)
    public BigInteger getOriginalEntryTraceNumber() {
        return originalEntryTraceNumber;
    }

    public CORAddendaRecord setOriginalEntryTraceNumber(BigInteger originalEntryTraceNumber) {
        this.originalEntryTraceNumber = originalEntryTraceNumber;
        return this;
    }

    @ACHField(start = 21, length = 5, inclusion = BLANK, name = RESERVED)
    public String getReserved() {
        return reserved(5);
    }

    @ACHField(start = 27, length = 7, inclusion = REQUIRED, name = ORIGINAL_RDFI_IDENTIFICATION)
    public Long getOriginalRDFIID() {
        return originalRDFIID;
    }

    public CORAddendaRecord setOriginalRDFIID(Long originalRDFIID) {
        this.originalRDFIID = originalRDFIID;
        return this;
    }

    @ACHField(start = 35, length = 28, inclusion = MANDATORY, name = CORRECTED_DATA)
    public String getCorrectedData() {
        return correctedData;
    }

    public CORAddendaRecord setCorrectedData(String correctedData) {
        this.correctedData = correctedData;
        return this;
    }

    @ACHField(start = 64, length = 14, inclusion = BLANK, name = RESERVED)
    public String getReserved2() {
        return reserved(14);
    }

    @ACHField(start = 79, length = 14, inclusion = MANDATORY, name = TRACE_NUMBER)
    public BigInteger getTraceNumber() {
        return traceNumber;
    }


    public CORAddendaRecord setTraceNumber(BigInteger traceNumber) {
        this.traceNumber = traceNumber;
        return this;
    }
}
