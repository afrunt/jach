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

import static com.afrunt.jach.annotation.InclusionRequirement.REQUIRED;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
public abstract class NonIATEntryDetail extends EntryDetail {
    public static final String DFI_ACCOUNT_NUMBER = "DFI Account Number";
    public static final String CHECK_SERIAL_NUMBER = "Check Serial Number";
    public static final String IDENTIFICATION_NUMBER = "Identification Number";
    public static final String RECEIVING_COMPANY_NAME = "Receiving Company Name";

    private String dfiAccountNumber;

    @ACHField(start = 12, length = 17, inclusion = REQUIRED, name = DFI_ACCOUNT_NUMBER)
    public String getDfiAccountNumber() {
        return this.dfiAccountNumber;
    }

    public NonIATEntryDetail setDfiAccountNumber(String dfiAccountNumber) {
        this.dfiAccountNumber = dfiAccountNumber;
        return this;
    }
}
