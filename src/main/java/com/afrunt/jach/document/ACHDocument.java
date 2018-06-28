/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.afrunt.jach.document;

import com.afrunt.jach.domain.FileControl;
import com.afrunt.jach.domain.FileHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("UnusedReturnValue")
public class ACHDocument {
    private FileHeader fileHeader;
    private List<ACHBatch> batches = new ArrayList<>();
    private FileControl fileControl;

    private int numberOfLines;

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

    public int getBatchCount() {
        return Optional.ofNullable(batches)
                .map(List::size)
                .orElse(0);
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public ACHDocument setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
        return this;
    }
}
