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
import static com.afrunt.jach.annotation.InclusionRequirement.OPTIONAL;

/**
 * @author Andrii Frunt
 */
@SuppressWarnings({"WeakerAccess", "EmptyMethod"})
@ACHRecordType(name = "Batch Header Record")
public class GeneralBatchHeader extends BatchHeader {
    public static final String COMPANY_NAME = "Company Name";
    public static final String COMPANY_DISCRETIONARY_DATA = "Company Discretionary Data";
    public static final String COMPANY_ID = "Company ID";
    public static final String COMPANY_DESCRIPTIVE_DATE = "Company Descriptive Date";
    private String companyName;
    private String companyDiscretionaryData;
    private String companyID;
    private String companyDescriptiveDate;

    @ACHField(start = 4, length = 16, name = COMPANY_NAME, inclusion = MANDATORY)
    public String getCompanyName() {
        return companyName;
    }


    public GeneralBatchHeader setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @ACHField(start = 20, length = 20, name = COMPANY_DISCRETIONARY_DATA, inclusion = OPTIONAL)
    public String getCompanyDiscretionaryData() {
        return companyDiscretionaryData;
    }

    public GeneralBatchHeader setCompanyDiscretionaryData(String companyDiscretionaryData) {
        this.companyDiscretionaryData = companyDiscretionaryData;
        return this;
    }

    @ACHField(start = 40, length = 10, name = COMPANY_ID, inclusion = MANDATORY)
    public String getCompanyID() {
        return companyID;
    }

    public GeneralBatchHeader setCompanyID(String companyID) {
        this.companyID = companyID;
        return this;
    }

    @Override
    @Values({
            "CCD", //Corporate Credit or Debit
            "CIE",
            "CTX", //Corporate Trade Exchange
            "PPD", //Prearranged Payment and Deposit Entry
            "ARC", //Accounts Receivable Check
            "BOC", //Back Office Conversion
            "POP", //Point-of-Purchase Entry
            "TEL", //Telephone Authorized Entry
            "WEB", //Internet Authorized Entry
            "RCK", //Represented Check Entry
            "POS"  //Point of Sale
    })
    public String getStandardEntryClassCode() {
        return super.getStandardEntryClassCode();
    }

    @ACHField(start = 63, length = 6, name = COMPANY_DESCRIPTIVE_DATE, inclusion = OPTIONAL)
    public String getCompanyDescriptiveDate() {
        return companyDescriptiveDate;
    }

    public GeneralBatchHeader setCompanyDescriptiveDate(String companyDescriptiveDate) {
        this.companyDescriptiveDate = companyDescriptiveDate;
        return this;
    }
}
