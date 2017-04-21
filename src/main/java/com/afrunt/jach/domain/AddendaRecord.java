package com.afrunt.jach.domain;

import com.afrunt.jach.annotation.ACHField;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
public abstract class AddendaRecord extends ACHRecord {
    public static final String ADDENDA_RECORD_TYPE_CODE = "7";
    public static final String ENTRY_DETAIL_SEQUENCE_NUMBER = "Entry Detail Sequence Number";
    public static final String ADDENDA_TYPE_CODE = "Addenda Type Code";
    public static final String TRACE_NUMBER = "Trace Number";

    @Override
    @ACHField(length = 1, name = RECORD_TYPE_CODE, inclusion = MANDATORY, values = ADDENDA_RECORD_TYPE_CODE,
            typeTag = true)
    public String getRecordTypeCode() {
        return ADDENDA_RECORD_TYPE_CODE;
    }

    @ACHField(start = 1, length = 2, name = AddendaRecord.ADDENDA_TYPE_CODE, inclusion = MANDATORY, typeTag = true)
    public abstract String getAddendaTypeCode();


}
