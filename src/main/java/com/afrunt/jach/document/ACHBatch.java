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

import com.afrunt.jach.domain.BatchControl;
import com.afrunt.jach.domain.BatchHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("UnusedReturnValue")
public class ACHBatch {
    private BatchHeader batchHeader;
    private List<ACHBatchDetail> details = new ArrayList<>();
    private BatchControl batchControl;

    public String getBatchType() {
        return Optional.ofNullable(batchHeader)
                .map(BatchHeader::getStandardEntryClassCode)
                .orElse(null);
    }

    public ACHBatch addDetail(ACHBatchDetail detail) {
        details = details == null ? new ArrayList<>() : details;
        details.add(detail);
        return this;
    }

    public BatchHeader getBatchHeader() {
        return batchHeader;
    }

    public ACHBatch setBatchHeader(BatchHeader batchHeader) {
        this.batchHeader = batchHeader;
        return this;
    }

    public List<ACHBatchDetail> getDetails() {
        return details;
    }

    public ACHBatch setDetails(List<ACHBatchDetail> details) {
        this.details = details;
        return this;
    }

    public BatchControl getBatchControl() {
        return batchControl;
    }

    public ACHBatch setBatchControl(BatchControl batchControl) {
        this.batchControl = batchControl;
        return this;
    }
}
