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

import static com.afrunt.jach.annotation.InclusionRequirement.*;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings({"WeakerAccess", "EmptyMethod"})
@ACHRecordType(name = "IAT Batch Header Record")
public class IATBatchHeader extends BatchHeader {
    public static final String IAT_INDICATOR = "SEC_IAT Indicator";
    public static final String FOREIGN_EXCHANGE_INDICATOR = "Foreign Exchange Indicator";
    public static final String FOREIGN_EXCHANGE_REFERENCE_INDICATOR = "Foreign Exchange Reference Indicator";
    public static final String FOREIGN_EXCHANGE_REFERENCE = "Foreign Exchange Reference";
    public static final String ISO_DESTINATION_COUNTRY_CODE = "ISO Destination Country Code";
    public static final String ORIGINATOR_ID = "Originator ID";
    public static final String ISO_ORIGINATING_COUNTRY_CODE = "ISO Originating Country Code";
    public static final String ISO_DESTINATION_CURRENCY_CODE = "ISO Destination Currency Code";

    private String IATIndicator;
    private String foreignExchangeIndicator;
    private String foreignExchangeReferenceIndicator;
    private String foreignExchangeReference;
    private String ISODestinationCountryCode;
    private String originatorID;
    private String ISOOriginatingCurrencyCode;
    private String ISODestinationCurrencyCode;

    @ACHField(start = 4, length = 16, name = IAT_INDICATOR, inclusion = OPTIONAL)
    public String getIATIndicator() {
        return IATIndicator;
    }

    public IATBatchHeader setIATIndicator(String IATIndicator) {
        this.IATIndicator = IATIndicator;
        return this;
    }

    @ACHField(start = 20, length = 2, name = FOREIGN_EXCHANGE_INDICATOR, inclusion = MANDATORY,
            values = {"FV", "FF"}, typeTag = true)
    public String getForeignExchangeIndicator() {
        return foreignExchangeIndicator;
    }

    public IATBatchHeader setForeignExchangeIndicator(String foreignExchangeIndicator) {
        this.foreignExchangeIndicator = foreignExchangeIndicator;
        return this;
    }

    @ACHField(start = 22, length = 1, name = FOREIGN_EXCHANGE_REFERENCE_INDICATOR, inclusion = REQUIRED,
            values = {"1", "2", "3"})
    public String getForeignExchangeReferenceIndicator() {
        return foreignExchangeReferenceIndicator;
    }

    public IATBatchHeader setForeignExchangeReferenceIndicator(String foreignExchangeReferenceIndicator) {
        this.foreignExchangeReferenceIndicator = foreignExchangeReferenceIndicator;
        return this;
    }

    //TODO: this field should be REQUIRED
    @ACHField(start = 23, length = 15, name = FOREIGN_EXCHANGE_REFERENCE, inclusion = OPTIONAL)
    public String getForeignExchangeReference() {
        return foreignExchangeReference;
    }

    public IATBatchHeader setForeignExchangeReference(String foreignExchangeReference) {
        this.foreignExchangeReference = foreignExchangeReference;
        return this;
    }

    @ACHField(start = 38, length = 2, name = ISO_DESTINATION_COUNTRY_CODE, inclusion = MANDATORY)
    public String getISODestinationCountryCode() {
        return ISODestinationCountryCode;
    }

    public IATBatchHeader setISODestinationCountryCode(String ISODestinationCountryCode) {
        this.ISODestinationCountryCode = ISODestinationCountryCode;
        return this;
    }

    @ACHField(start = 40, length = 10, name = ORIGINATOR_ID, inclusion = MANDATORY)
    public String getOriginatorID() {
        return originatorID;
    }

    public IATBatchHeader setOriginatorID(String originatorID) {
        this.originatorID = originatorID;
        return this;
    }

    @Override
    @Values("IAT")
    public String getStandardEntryClassCode() {
        return super.getStandardEntryClassCode();
    }

    @Override

    public BatchHeader setStandardEntryClassCode(String standardEntryClassCode) {
        return super.setStandardEntryClassCode(standardEntryClassCode);
    }

    @ACHField(start = 63, length = 3, name = ISO_ORIGINATING_COUNTRY_CODE, inclusion = MANDATORY)
    public String getISOOriginatingCurrencyCode() {
        return ISOOriginatingCurrencyCode;
    }

    public IATBatchHeader setISOOriginatingCurrencyCode(String ISOOriginatingCurrencyCode) {
        this.ISOOriginatingCurrencyCode = ISOOriginatingCurrencyCode;
        return this;
    }

    @ACHField(start = 66, length = 3, name = ISO_DESTINATION_CURRENCY_CODE, inclusion = MANDATORY)
    public String getISODestinationCurrencyCode() {
        return ISODestinationCurrencyCode;
    }

    public IATBatchHeader setISODestinationCurrencyCode(String ISODestinationCurrencyCode) {
        this.ISODestinationCurrencyCode = ISODestinationCurrencyCode;
        return this;
    }
}
