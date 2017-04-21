package com.afrunt.jach.domain;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.DateFormat;
import com.afrunt.jach.annotation.Values;

import java.util.Date;

import static com.afrunt.jach.annotation.InclusionRequirement.*;
import static com.afrunt.jach.domain.RecordTypes.Constants.FILE_HEADER_RECORD_TYPE_CODE;

/**
 * @author Andrii Frunt
 */
@ACHRecordType
public class FileHeader extends ACHRecord {
    public static final String PRIORITY_CODE = "Priority Code";
    public static final String IMMEDIATE_DESTINATION = "Immediate Destination";
    public static final String IMMEDIATE_ORIGIN = "Immediate Origin";
    public static final String FILE_CREATION_DATE = "File Creation Date";
    public static final String FILE_CREATION_TIME = "File Creation Time";
    public static final String FILE_ID_MODIFIER = "File ID Modifier";
    public static final String RECORD_SIZE = "ACHRecord Size";
    public static final String BLOCKING_FACTOR = "Blocking Factor";
    public static final String FORMAT_CODE = "Format Code";
    public static final String IMMEDIATE_DESTINATION_NAME = "Immediate Destination Name";
    public static final String IMMEDIATE_ORIGIN_NAME = "Immediate Origin Name";
    public static final String REFERENCE_CODE = "Reference Code";

    private String priorityCode;
    private String immediateDestination;
    private String immediateOrigin;
    private Date fileCreationDate;
    private String fileCreationTime;
    private String fileIdModifier;
    private String blockingFactor;
    private String formatCode;
    private String immediateDestinationName;
    private String immediateOriginName;
    private String referenceCode;

    @Override
    @Values(FILE_HEADER_RECORD_TYPE_CODE)
    public String getRecordTypeCode() {
        return FILE_HEADER_RECORD_TYPE_CODE;
    }

    /**
     * Priority codes are not used at this time; this field must contain 01
     *
     * @return 01
     */
    @ACHField(start = 1, length = 2, name = PRIORITY_CODE, inclusion = REQUIRED, values = "01")
    public String getPriorityCode() {
        return priorityCode;
    }

    public FileHeader setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
        return this;
    }

    @ACHField(start = 3, length = 10, name = IMMEDIATE_DESTINATION, inclusion = MANDATORY)
    public String getImmediateDestination() {
        return immediateDestination;
    }

    public FileHeader setImmediateDestination(String immediateDestination) {
        this.immediateDestination = immediateDestination;
        return this;
    }

    @ACHField(start = 13, length = 10, name = IMMEDIATE_ORIGIN, inclusion = MANDATORY)
    public String getImmediateOrigin() {
        return immediateOrigin;
    }

    public FileHeader setImmediateOrigin(String immediateOrigin) {
        this.immediateOrigin = immediateOrigin;
        return this;
    }

    @ACHField(start = 23, length = 6, name = FILE_CREATION_DATE, inclusion = MANDATORY)
    @DateFormat("yyMMdd")
    public Date getFileCreationDate() {
        return fileCreationDate;
    }

    public FileHeader setFileCreationDate(Date fileCreationDate) {
        this.fileCreationDate = fileCreationDate;
        return this;
    }

    @ACHField(start = 29, length = 4, name = FILE_CREATION_TIME)
    public String getFileCreationTime() {
        return fileCreationTime;
    }

    public FileHeader setFileCreationTime(String fileCreationTime) {
        this.fileCreationTime = fileCreationTime;
        return this;
    }

    @ACHField(start = 33, length = 1, name = FILE_ID_MODIFIER, inclusion = MANDATORY)
    public String getFileIdModifier() {
        return fileIdModifier;
    }

    public FileHeader setFileIdModifier(String fileIdModifier) {
        this.fileIdModifier = fileIdModifier;
        return this;
    }

    /**
     * This field indicates the number of characters contained in each record. The value 094 is used
     *
     * @return the number of characters contained in each record
     */
    @ACHField(start = 34, length = 3, name = RECORD_SIZE, inclusion = MANDATORY, values = "094")
    public String getRecordSize() {
        return "094";
    }

    @ACHField(start = 37, length = 2, name = BLOCKING_FACTOR, inclusion = MANDATORY, values = "10")
    public String getBlockingFactor() {
        return blockingFactor;
    }

    public FileHeader setBlockingFactor(String blockingFactor) {
        this.blockingFactor = blockingFactor;
        return this;
    }

    @ACHField(start = 39, length = 1, values = "1", inclusion = MANDATORY, name = FORMAT_CODE)
    public String getFormatCode() {
        return formatCode;
    }

    public FileHeader setFormatCode(String formatCode) {
        this.formatCode = formatCode;
        return this;
    }

    @ACHField(start = 40, length = 23, name = IMMEDIATE_DESTINATION_NAME, inclusion = OPTIONAL)
    public String getImmediateDestinationName() {
        return immediateDestinationName;
    }

    public FileHeader setImmediateDestinationName(String immediateDestinationName) {
        this.immediateDestinationName = immediateDestinationName;
        return this;
    }

    @ACHField(start = 63, length = 23, name = IMMEDIATE_ORIGIN_NAME, inclusion = OPTIONAL)
    public String getImmediateOriginName() {
        return immediateOriginName;
    }

    public FileHeader setImmediateOriginName(String immediateOriginName) {
        this.immediateOriginName = immediateOriginName;
        return this;
    }

    @ACHField(start = 86, length = 8, name = REFERENCE_CODE, inclusion = OPTIONAL)
    public String getReferenceCode() {
        return referenceCode;
    }

    public FileHeader setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
        return this;
    }
}
