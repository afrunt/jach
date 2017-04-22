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
package com.afrunt.jach.domain.addenda.iat;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.domain.AddendaRecord;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
public abstract class IATAddendaRecord extends AddendaRecord {
    private Long entryDetailSequenceNumber;

    /**
     * This field contains the ascending sequence number section of the Entry Detail Record‟s trace number.
     * This number is the same as the last seven digits of the trace number (ACHField 13) of the related Entry Detail
     * Record.
     *
     * @return Entry Detail Sequence Number
     */
    @ACHField(start = 87, length = 7, name = ENTRY_DETAIL_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Long getEntryDetailSequenceNumber() {
        return entryDetailSequenceNumber;
    }

    public AddendaRecord setEntryDetailSequenceNumber(Long entryDetailSequenceNumber) {
        this.entryDetailSequenceNumber = entryDetailSequenceNumber;
        return this;
    }
}
