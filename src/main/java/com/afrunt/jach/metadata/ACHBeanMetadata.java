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
package com.afrunt.jach.metadata;

import com.afrunt.beanmetadata.BeanMetadata;
import com.afrunt.jach.annotation.ACHField;

import java.util.List;
import java.util.Set;

/**
 * @author Andrii Frunt
 */
public class ACHBeanMetadata extends BeanMetadata<ACHFieldMetadata> {
    public Set<ACHFieldMetadata> getACHFieldsMetadata() {
        return getFieldsAnnotatedWith(ACHField.class);
    }

    public boolean recordTypeCodeIs(String recordTypeCode) {
        return recordTypeCode.equals(getRecordTypeCode());
    }

    public String getRecordTypeCode() {
        List<String> constantValues = getFieldMetadata("recordTypeCode").getValues();
        return constantValues.get(0);
    }
}
