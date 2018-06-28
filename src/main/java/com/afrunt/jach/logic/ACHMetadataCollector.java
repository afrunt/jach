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

import com.afrunt.beanmetadata.MetadataCollector;
import com.afrunt.jach.metadata.ACHBeanMetadata;
import com.afrunt.jach.metadata.ACHFieldMetadata;
import com.afrunt.jach.metadata.ACHMetadata;

import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @author Andrii Frunt
 */
public class ACHMetadataCollector extends MetadataCollector<ACHMetadata, ACHBeanMetadata, ACHFieldMetadata> implements ACHErrorMixIn {

    @Override
    protected ACHMetadata newMetadata() {
        return new ACHMetadata();
    }

    @Override
    protected ACHBeanMetadata newBeanMetadata() {
        return new ACHBeanMetadata();
    }

    @Override
    protected ACHFieldMetadata newFieldMetadata() {
        return new ACHFieldMetadata();
    }

    @Override
    protected void validateFieldMetadata(ACHFieldMetadata fm) {
        super.validateFieldMetadata(fm);
        boolean achField = fm.isACHField();

        if (achField) {
            if (fm.isBlank() && fm.hasConstantValues()) {
                throwError("ACHField cannot be BLANK and contain constant values");
            }

            if (fm.isTypeTag() && (!fm.isMandatory() || fm.getValues().isEmpty())) {
                throwError("TypeTag field should have some constant values and be mandatory " + fm);
            }

            validateDateField(fm);
        }
    }

    @Override
    protected boolean skipClass(Class<?> cl) {
        return super.skipClass(cl) || Modifier.isAbstract(cl.getModifiers());
    }

    private void validateDateField(ACHFieldMetadata fm) {
        if (fm.isDate()) {
            String dateFormat = fm.getDateFormat();
            if (dateFormat == null) {
                throwError("Date format is required for date fields " + fm);
            }

            try {
                new SimpleDateFormat(Objects.requireNonNull(dateFormat));
            } catch (Exception e) {
                throwError(dateFormat + " is wrong date format for field " + fm);
            }

            if (dateFormat.length() != fm.getLength()) {
                throwError("The length of date pattern should be equal to field length");
            }
        }
    }
}
