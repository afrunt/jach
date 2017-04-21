package com.afrunt.jach.domain;

import com.afrunt.jach.ACHException;
import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
public abstract class ACHRecord {
    public static final String RECORD_TYPE_CODE = "ACH Record Type Code";
    public static final String RESERVED = "RESERVED";
    private String record;

    @ACHField(length = 1, inclusion = MANDATORY, name = RECORD_TYPE_CODE, typeTag = true)
    public abstract String getRecordTypeCode();

    public String getRecord() {
        return record;
    }

    public ACHRecord setRecord(String record) {
        this.record = record;
        return this;
    }

    public String reserved(int length) {
        return StringUtils.left("", length);
    }

    public void setFieldValue(ACHFieldMetadata fm, Object value) {
        if (!fm.isReadOnly()) {
            try {
                fm.getSetter().invoke(this, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ACHException("Unable set value for the field " + fm, e);
            }
        }
    }

    public Object getFieldValue(ACHFieldMetadata fm) {
        try {
            return fm.getGetter().invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ACHException("Error retrieving value from the field " + fm, e);
        }
    }

    public boolean is(RecordTypes type) {
        return getRecordTypeCode().equals(type.getRecordTypeCode());
    }
}
