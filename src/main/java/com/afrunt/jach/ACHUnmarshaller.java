package com.afrunt.jach;

import com.afrunt.jach.document.ACHBatch;
import com.afrunt.jach.document.ACHBatchDetail;
import com.afrunt.jach.document.ACHDocument;
import com.afrunt.jach.domain.*;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHRecordTypeMetadata;
import com.afrunt.jach.metadata.MetadataCollector;
import com.sun.deploy.util.StringUtils;

import java.io.InputStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import static com.afrunt.jach.domain.RecordTypes.*;

/**
 * @author Andrii Frunt
 */
public class ACHUnmarshaller extends ACHProcessor {
    private ResourceBundle messages = ResourceBundle.getBundle("ach_messages");

    private MetadataCollector metadataCollector = new MetadataCollector();

    public ACHUnmarshaller() {
        super(new MetadataCollector());
    }

    public ACHUnmarshaller(MetadataCollector metadataCollector) {
        super(metadataCollector);
        this.metadataCollector = metadataCollector;
    }

    public ACHDocument unmarshal(InputStream is) {
        return new StatefulUnmarshaller().unmarshalDocument(is);
    }

    public ACHRecord unmarshalRecord(String line) {
        return unmarshalRecord(line, typeOfRecord(line));
    }

    public ACHRecord unmarshalRecord(String line, ACHRecordTypeMetadata recordType) {
        if (recordType != null) {

            List<String> strings = splitString(line, recordType);

            int i = 0;
            ACHRecord record = recordType.createInstance();
            record.setRecord(line);

            for (ACHFieldMetadata fm : recordType.getFieldsMetadata()) {
                String valueString = strings.get(i);
                Object value = fieldValueFromString(valueString, fm);

                if (!fm.isReadOnly()) {

                    if (fm.hasConstantValues() && fm.valueSatisfiesToConstantValues(valueString)) {
                        record.setFieldValue(fm, value);

                    } else if (fm.hasConstantValues() && fm.valueSatisfiesToConstantValues(valueString)) {
                        throw new ACHException(String.format("%s is wrong value for field %s. Valid values are %s",
                                valueString, fm, StringUtils.join(fm.getPossibleValues(), ","))
                        );
                    }

                    record.setFieldValue(fm, value);
                }


                i++;
            }
            return record;
        } else {
            return new ACHRecord() {
                @Override
                public String getRecordTypeCode() {
                    return null;
                }
            }.setRecord(line);
        }
    }

    private class StatefulUnmarshaller {
        private int lineNumber = 0;
        private String currentLine;
        private ACHDocument document = new ACHDocument();
        private ACHBatch currentBatch;
        private ACHBatchDetail currentDetail;

        public ACHDocument unmarshalDocument(InputStream is) {
            Scanner sc = new Scanner(is);

            while (sc.hasNextLine()) {
                ++lineNumber;
                currentLine = sc.nextLine();
                validateLine();

                ACHRecord record = unmarshalRecord(currentLine, findRecordType());

                if (record.is(FILE_HEADER)) {
                    document.setFileHeader((FileHeader) record);
                } else if (record.is(FILE_CONTROL)) {
                    document.setFileControl((FileControl) record);
                    return document;
                } else if (record.is(BATCH_HEADER)) {
                    currentBatch = new ACHBatch().setBatchHeader((BatchHeader) record);
                    document.addBatch(currentBatch);
                } else if (record.is(BATCH_CONTROL)) {
                    currentBatch.setBatchControl((BatchControl) record);
                    currentBatch = null;
                    currentDetail = null;
                } else if (record.is(ENTRY_DETAIL)) {
                    currentDetail = new ACHBatchDetail()
                            .setDetailRecord((EntryDetail) record);
                    currentBatch.addDetail(currentDetail);

                } else if (record.is(ADDENDA)) {
                    currentDetail.addAddendaRecord((AddendaRecord) record);
                }
            }

            return document;
        }

        private void validateLine() {
            String recordTypeCode = extractRecordTypeCode(currentLine);

            if (!RecordTypes.validRecordTypeCode(recordTypeCode)) {
                error("Unknown record type code " + recordTypeCode);
            }

            if ((lineNumber == 1) && !FILE_HEADER.is(currentLine)) {
                error("First line should be the file header, that start with 1");
            }

            if (RecordTypes.BATCH_CONTROL.is(currentLine) && currentBatch == null) {
                error("Unexpected batch control record");
            }

            if (RecordTypes.BATCH_HEADER.is(currentLine) && currentBatch != null) {
                error("Unexpected batch header record");
            }

            if (RecordTypes.ENTRY_DETAIL.is(currentLine) && currentBatch == null) {
                error("Unexpected entry detail record");
            }

            if (RecordTypes.ADDENDA.is(currentLine) && currentDetail == null) {
                error("Unexpected addenda record");
            }
        }

        private ACHRecordTypeMetadata findRecordType() {
            if (!ENTRY_DETAIL.is(currentLine)) {
                return typeOfRecord(currentLine);
            } else {
                Set<ACHRecordTypeMetadata> entryDetailTypes = typesForRecordTypeCode(ENTRY_DETAIL.getRecordTypeCode());
                String batchType = currentBatch.getBatchType();
                return entryDetailTypes.stream()
                        .filter(t -> t.getRecordClassName().startsWith(batchType))
                        .findFirst()
                        .orElseThrow(() -> new ACHException("Type of detail record not found for string: " + currentLine));
            }
        }

        private void error(String message) {
            throw new ACHException("Line " + lineNumber + ". " + message);
        }
    }
}
