/**
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
package com.afrunt.jach;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Andrii Frunt
 */
public class ACHValueConstants {
    public static final String FILLED_WITH_ZEROS = "com.afrunt.jach.valid.value.filled.with.zeros";
    public static final String FILLED_WITH_SPACES = "com.afrunt.jach.valid.value.filled.with.spaces";
    public static final String DOLLAR_AMOUNT = "com.afrunt.jach.valid.value.dollar.amount";
    public static final Map<String, Predicate<String>> VALIDATORS;

    static {
        Map<String, Predicate<String>> vMap = new HashMap<>();

        vMap.put(FILLED_WITH_ZEROS, s -> s.replace("0", "").equals(""));
        vMap.put(FILLED_WITH_SPACES, s -> s.replace(" ", "").equals(""));
        vMap.put(DOLLAR_AMOUNT, s -> {
            try {
                new BigDecimal(s.trim());
                return true;
            } catch (Exception e) {
                return false;
            }
        });


        VALIDATORS = Collections.unmodifiableMap(vMap);
    }

    public static boolean isSpecialValueConstant(String v) {
        return VALIDATORS.keySet().contains(v);
    }

    public static boolean validSpecialValue(String value, String specialConstant) {
        return VALIDATORS.getOrDefault(specialConstant, v -> false).test(value);
    }
}
