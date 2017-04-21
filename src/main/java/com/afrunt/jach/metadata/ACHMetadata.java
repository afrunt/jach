package com.afrunt.jach.metadata;

import com.afrunt.jach.ACHException;
import com.afrunt.jach.domain.ACHRecord;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
public class ACHMetadata {
    private Set<ACHRecordTypeMetadata> recordTypes;

    public ACHMetadata(Set<ACHRecordTypeMetadata> recordTypes) {
        this.recordTypes = recordTypes;
    }

    public Set<ACHRecordTypeMetadata> typesForRecordTypeCode(String recordTypeCode) {
        return recordTypes.stream()
                .filter(rt -> rt.recordTypeCodeIs(recordTypeCode))
                .collect(Collectors.toSet());
    }

    public ACHRecordTypeMetadata typeOfRecord(ACHRecord record) {
        return recordTypes.stream()
                .filter(rt -> rt.getType().equals(record.getClass()))
                .findFirst()
                .orElseThrow(() -> new ACHException("Metadata not found for type " + record.getClass()));
    }
}
