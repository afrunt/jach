package com.afrunt.jach.document;

import com.afrunt.jach.domain.FileControl;
import com.afrunt.jach.domain.FileHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrii Frunt
 */
public class ACHDocument {
    private FileHeader fileHeader;
    private List<ACHBatch> batches = new ArrayList<>();
    private FileControl fileControl;

    public ACHDocument addBatch(ACHBatch batch) {
        batches = batches == null ? new ArrayList<>() : batches;
        batches.add(batch);
        return this;
    }

    public FileHeader getFileHeader() {
        return fileHeader;
    }

    public ACHDocument setFileHeader(FileHeader fileHeader) {
        this.fileHeader = fileHeader;
        return this;
    }

    public List<ACHBatch> getBatches() {
        return batches;
    }

    public ACHDocument setBatches(List<ACHBatch> batches) {
        this.batches = batches;
        return this;
    }

    public FileControl getFileControl() {
        return fileControl;
    }

    public ACHDocument setFileControl(FileControl fileControl) {
        this.fileControl = fileControl;
        return this;
    }

    public int getBatchCount(){
        return Optional.ofNullable(batches).map(List::size).orElse(0);
    }
}
