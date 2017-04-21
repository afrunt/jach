package com.afrunt.jach.metadata;

import com.afrunt.jach.ACHException;
import com.afrunt.jach.domain.ACHRecord;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHRecordTypeMetadata {
    private Class<? extends ACHRecord> type;
    private Set<ACHFieldMetadata> fieldsMetadata;
    private String recordTypeCode;

    public ACHRecordTypeMetadata(Class<? extends ACHRecord> type, Set<ACHFieldMetadata> fieldsMetadata) {
        this.type = type;
        this.fieldsMetadata = new TreeSet<>(fieldsMetadata.stream()
                .sorted().collect(Collectors.toList()));
    }

    public String getRecordTypeCode() {
        ACHFieldMetadata recordTypeCodeField = getFieldsMetadata().stream()
                .filter(ACHFieldMetadata::isRecordTypeCode)
                .findFirst()
                .orElseThrow(() -> new ACHException("ACHRecord type code not found for " + this));

        return recordTypeCodeField.getValues().stream()
                .findFirst()
                .orElseThrow(() -> new ACHException("ACHRecord type code value not found for " + this));
    }

    public boolean recordTypeCodeIs(String recordTypeCode) {
        return getRecordTypeCode().equals(recordTypeCode);
    }

    public ACHRecord createInstance() {
        try {
            return getType().newInstance();
        } catch (Exception e) {
            throw new ACHException("Error during record instantiation " + type);
        }
    }

    public String getRecordClassName() {
        String name = type.getName();
        if (name.contains(".")) {
            return name.substring(name.lastIndexOf(".") + 1);
        } else {

            return name;
        }
    }

    public Class<? extends ACHRecord> getType() {
        return type;
    }

    public Set<ACHFieldMetadata> getFieldsMetadata() {
        return fieldsMetadata;
    }

    @Override
    public String toString() {
        String fullName = type.getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }

}
