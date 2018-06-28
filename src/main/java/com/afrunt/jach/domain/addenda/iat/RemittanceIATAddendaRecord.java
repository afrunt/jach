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
import com.afrunt.jach.annotation.ACHRecordType;
import com.afrunt.jach.annotation.Values;

import static com.afrunt.jach.annotation.InclusionRequirement.MANDATORY;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings("WeakerAccess")
@ACHRecordType(name = "Remittance IAT Addenda Record")
public class RemittanceIATAddendaRecord extends IATAddendaRecord {
    public static final String REMITTANCE_IAT_ADDENDA_TYPE_CODE = "17";
    public static final String PAYMENT_RELATED_INFORMATION = "Payment Related Information";
    public static final String ADDENDA_SEQUENCE_NUMBER = "Addenda Sequence Number";
    private String paymentRelatedInformation;
    private Integer addendaSequenceNumber;

    @Override
    @Values(REMITTANCE_IAT_ADDENDA_TYPE_CODE)
    public String getAddendaTypeCode() {
        return REMITTANCE_IAT_ADDENDA_TYPE_CODE;
    }

    @ACHField(start = 3, length = 80, name = PAYMENT_RELATED_INFORMATION)
    public String getPaymentRelatedInformation() {
        return paymentRelatedInformation;
    }

    public RemittanceIATAddendaRecord setPaymentRelatedInformation(String paymentRelatedInformation) {
        this.paymentRelatedInformation = paymentRelatedInformation;
        return this;
    }

    @ACHField(start = 83, length = 4, name = ADDENDA_SEQUENCE_NUMBER, inclusion = MANDATORY)
    public Integer getAddendaSequenceNumber() {
        return addendaSequenceNumber;
    }

    public RemittanceIATAddendaRecord setAddendaSequenceNumber(Integer addendaSequenceNumber) {
        this.addendaSequenceNumber = addendaSequenceNumber;
        return this;
    }
}
