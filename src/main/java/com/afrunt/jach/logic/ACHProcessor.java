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
package com.afrunt.jach.logic;

import com.afrunt.jach.ACH;
import com.afrunt.jach.exception.ACHException;
import com.afrunt.jach.metadata.ACHMetadata;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Andrii Frunt
 */
class ACHProcessor {
    static final String LINE_SEPARATOR = Optional.ofNullable(System.getProperty("line.separator")).orElse("\n");

    private ACHMetadataCollector metadataCollector;

    ACHProcessor(ACHMetadataCollector metadataCollector) {
        this.metadataCollector = metadataCollector;
    }

    ACHMetadata getMetadata() {
        return metadataCollector.collectMetadata(ACH.ACH_CLASSES);
    }

    void throwError(String message) throws ACHException {
        throw error(message);
    }

    ACHException error(String message) {
        return new ACHException(message);
    }

    ACHException error(String message, Throwable e) {
        return new ACHException(message, e);
    }

    protected BigDecimal decimalAdjuster(int digitsAfterComma) {
        return BigDecimal.TEN.pow(digitsAfterComma);
    }

}
