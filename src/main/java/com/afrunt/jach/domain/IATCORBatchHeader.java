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
package com.afrunt.jach.domain;

import com.afrunt.jach.annotation.ACHField;
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings({"WeakerAccess", "EmptyMethod"})
@ACHRecordType(name = "IAT Company/Batch Header Record For Notification Of Change (NOC)")
class IATCORBatchHeader extends IATBatchHeader {
    @Override
    @ACHField(start = 4, length = 16, name = IAT_INDICATOR, values = "IATCOR", inclusion = MANDATORY, typeTag = true)
    public String getIATIndicator() {
        return super.getIATIndicator();
    }

    @Override
    @Values("COR")
    public String getStandardEntryClassCode() {
        return super.getStandardEntryClassCode();
    }
}
