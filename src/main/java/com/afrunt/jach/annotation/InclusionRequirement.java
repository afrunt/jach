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
package com.afrunt.jach.annotation;

/**
 * @author Andrii Frunt
 */
public enum InclusionRequirement {
    /**
     * A “Mandatory” field contains information necessary to ensure the proper routing and/or posting of an ACH entry.
     * The ACH Operator will reject any entry or batch, which does not have appropriate values in a Mandatory field.
     */
    MANDATORY,

    /**
     * The omission of a “Required” field will not cause an entry reject at the ACH Operator, but may cause a reject at
     * the RDFI. For example, if the DFI Account Number field in the Entry Detail ACHRecord is omitted, the RDFI may return
     * the entry because it cannot be posted. You should include appropriate values in “Required” fields to avoid
     * processing and control problems at the RDFI.
     */
    REQUIRED,

    /**
     * The inclusion or omission of an “Optional” data field is at the discretion of the Originator. If you do include
     * optional fields, the RDFI must include them in any return.
     */
    OPTIONAL,

    /**
     * Mark the fields, that should always be blank
     */
    BLANK
}
