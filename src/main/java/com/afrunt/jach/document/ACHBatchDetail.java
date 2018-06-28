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

import com.afrunt.jach.domain.AddendaRecord;
import com.afrunt.jach.domain.EntryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("UnusedReturnValue")
public class ACHBatchDetail {
    private EntryDetail detailRecord;
    private List<AddendaRecord> addendaRecords = new ArrayList<>();

    public ACHBatchDetail addAddendaRecord(AddendaRecord addendaRecord) {
        addendaRecords = addendaRecords == null ? new ArrayList<>() : addendaRecords;
        addendaRecords.add(addendaRecord);
        return this;
    }

    public EntryDetail getDetailRecord() {
        return detailRecord;
    }

    public ACHBatchDetail setDetailRecord(EntryDetail detailRecord) {
        this.detailRecord = detailRecord;
        return this;
    }

    public List<AddendaRecord> getAddendaRecords() {
        return addendaRecords;
    }

    public ACHBatchDetail setAddendaRecords(List<AddendaRecord> addendaRecords) {
        this.addendaRecords = addendaRecords;
        return this;
    }
}
