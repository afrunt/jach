package com.afrunt.jach.test;

import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;
import com.afrunt.jach.domain.ACHRecord;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class TestEntryDetail extends ACHRecord {
    @Override
    @Values("6")
    public String getRecordTypeCode() {
        return "6";
    }
}
