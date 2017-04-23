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

import com.afrunt.jach.annotation.*;
import com.afrunt.jach.domain.ACHRecord;
import com.afrunt.jach.exception.ACHException;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * @author Andrii Frunt
 */
public class MetadataCollector {
    private Set<ACHRecordTypeMetadata> metadata;

    public MetadataCollector() {
    }


    public ACHMetadata collectMetadata() {
        return new ACHMetadata(collectTypesMetadata());
    }

    public Set<ACHRecordTypeMetadata> collectTypesMetadata() {
        if (metadata == null) {
            Set<ACHRecordTypeMetadata> collectedMetadata = findACHRecordTypes().stream()
                    .map(this::collectRecordTypeMetadata)
                    .collect(Collectors.toSet());
            metadata = Collections.unmodifiableSet(collectedMetadata);
        }

        return metadata;
    }

    public Set<ACHRecordTypeMetadata> collectMetadataForRecordTypeCode(String code) {
        return collectTypesMetadata().stream()
                .filter(m -> code.equals(m.getRecordTypeCode()))
                .collect(Collectors.toSet());
    }

    public List<Class<?>> achRecordClassHierarchy(Class<?> cl) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (!Object.class.equals(cl)) {
            hierarchy.add(cl);
            cl = cl.getSuperclass();
        }

        Collections.reverse(hierarchy);
        return hierarchy;
    }

    public Set<Method> findAnnotatedGetters(Class<?> cl) {
        return Arrays.stream(cl.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("get")
                        && !Void.class.equals(m.getReturnType())
                        && m.isAnnotationPresent(ACHField.class)
                        && ACHFieldMetadata.VALID_FIELD_TYPES.contains(m.getReturnType())
                )
                .collect(Collectors.toSet());
    }

    private ACHRecordTypeMetadata collectRecordTypeMetadata(Class<? extends ACHRecord> type) {
        return validateTypeMetadata(new ACHRecordTypeMetadata(type, collectFieldsMetadata(type)));
    }

    private Set<ACHFieldMetadata> collectFieldsMetadata(Class<? extends ACHRecord> type) {
        return new TreeSet<>(
                findACHRecordTypes().stream()
                        .filter(t -> t.equals(type))
                        .findFirst()
                        .map(t -> collectFieldsMetadata(achRecordClassHierarchy(t), new HashMap<>()))
                        .orElseThrow(() -> new ACHException("Type is not an ACH record " + type))
        );
    }

    private List<ACHFieldMetadata> collectFieldsMetadata(List<Class<?>> types, Map<String, ACHFieldMetadata> acc) {
        if (types.isEmpty()) {
            return new ArrayList<>(acc.values()).stream().sorted().collect(Collectors.toList());
        } else {
            Class<?> type = types.get(0);
            Set<Method> getters = findAnnotatedGetters(type);

            for (Method getter : getters) {
                String fieldName = fieldNameFromGetter(getter);
                ACHFieldMetadata fieldMetadata = acc.getOrDefault(fieldName, new ACHFieldMetadata());
                fieldMetadata = metadataFromGetter(type, getter, fieldMetadata);

                acc.put(fieldName, fieldMetadata);
            }

            for (String fieldName : acc.keySet()) {
                ACHFieldMetadata fieldMetadata = acc.get(fieldName);
                Method getter = getterForField(type, fieldName, fieldMetadata.getFieldType());
                if (getter != null) {
                    acc.put(fieldName, metadataFromGetter(type, getter, fieldMetadata));
                }
            }

            List<Class<?>> tail = new ArrayList<>(types);
            tail.remove(type);

            return collectFieldsMetadata(tail, acc);
        }
    }

    private String getterNameFromFieldName(String fieldName) {
        return "get" + capitalize(fieldName);
    }

    private Method getterForField(Class<?> cl, String fieldName, Class<?> fieldType) {
        return Arrays.stream(cl.getDeclaredMethods())
                .filter(m -> m.getName().equals(getterNameFromFieldName(fieldName)) && m.getReturnType().equals(fieldType))
                .findFirst()
                .orElse(null);
    }

    private ACHFieldMetadata metadataFromGetter(Class<?> cl, Method getter, ACHFieldMetadata fieldMetadata) {
        fieldMetadata = fieldDefinition(getter, fieldMetadata);
        fieldMetadata
                .setName(fieldNameFromGetter(getter))
                .setGetter(getter)
                .setFieldType(getter.getReturnType());

        Method setter = findSetterForGetter(cl, getter);

        if (setter != null) {
            fieldMetadata.setSetter(setter);
        }

        fieldMetadata = fieldDefinition(getter, fieldMetadata);

        fieldMetadata = valuesDefinition(getter, fieldMetadata);

        fieldMetadata = inclusionDefinition(getter, fieldMetadata);

        fieldMetadata = dateFormatDefinition(getter, fieldMetadata);

        return fieldMetadata;
    }

    private ACHFieldMetadata fieldDefinition(Method getter, ACHFieldMetadata fieldMetadata) {
        return Optional.ofNullable(getter.getAnnotation(ACHField.class))
                .map(annotation ->
                        fieldMetadata.setStart(annotation.start())
                                .setLength(annotation.length())
                                .setAchFieldName(annotation.name())
                                .setValues(new HashSet<>(Arrays.asList(annotation.values())))
                                .setInclusion(annotation.inclusion())
                                .setDateFormat(annotation.dateFormat())
                                .setTypeTag(annotation.typeTag())
                )
                .orElse(fieldMetadata);
    }

    private ACHFieldMetadata valuesDefinition(Method getter, ACHFieldMetadata fieldMetadata) {
        return ofNullable(getter.getAnnotation(Values.class))
                .map(a -> fieldMetadata.setValues(a.value()))
                .orElse(fieldMetadata);
    }

    private ACHFieldMetadata inclusionDefinition(Method getter, ACHFieldMetadata fieldMetadata) {
        return ofNullable(getter.getAnnotation(Inclusion.class))
                .map(a -> fieldMetadata.setInclusion(a.value()))
                .orElse(fieldMetadata);
    }

    private ACHFieldMetadata dateFormatDefinition(Method getter, ACHFieldMetadata fieldMetadata) {
        return ofNullable(getter.getAnnotation(DateFormat.class))
                .map(a -> fieldMetadata.setDateFormat(a.value()))
                .orElse(fieldMetadata);
    }

    private Method findSetterForGetter(Class<?> cl, Method getter) {
        try {
            String setterName = "set" + capitalize(fieldNameFromGetter(getter));
            return cl.getDeclaredMethod(setterName, getter.getReturnType());
        } catch (NoSuchMethodException e) {
            //ACHField without setter
            return null;
        }
    }

    private String fieldNameFromGetter(Method getter) {
        return uncapitalize(getter.getName().substring(3));
    }

    private Set<Class<? extends ACHRecord>> findACHRecordTypes() {
        return Stream.of("com.afrunt.jach.domain")
                .map(pkg -> new Reflections(pkg))
                .map(rfl -> rfl.getSubTypesOf(ACHRecord.class))
                .flatMap(Set::stream)
                .filter(c -> c.isAnnotationPresent(ACHRecordType.class)
                        && !Modifier.isAbstract(c.getModifiers())
                        && !c.isInterface()
                )
                .collect(Collectors.toSet());
    }

    private String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    private String uncapitalize(String str) {
        return StringUtils.uncapitalize(str);
    }


    private ACHRecordTypeMetadata validateTypeMetadata(ACHRecordTypeMetadata tm) {
        tm.getFieldsMetadata().forEach(this::validateFieldMetadata);
        return tm;
    }

    private void validateFieldMetadata(ACHFieldMetadata fm) {
        if (fm.isBlank() && fm.hasConstantValues()) {
            throw new ACHException("ACHField cannot be BLANK and contain constant values");
        }

        if (fm.isTypeTag() && (!fm.isMandatory() || fm.getConstantValues().isEmpty())) {
            throw new ACHException("TypeTag field should have some constant values and be mandatory");
        }

        if (fm.isDate()) {
            if (fm.getDateFormat().equals(ACHField.EMPTY_DATE_PATTERN)) {
                throw new ACHException("Date format is required for date fields");
            }

            if (fm.getDateFormat().length() > fm.getLength()) {
                throw new ACHException("Date format exceeds the maximum length of the field " + fm + " [" + fm.getLength() + "]");
            }

            try {
                new SimpleDateFormat(fm.getDateFormat());
            } catch (Exception e) {
                throw new ACHException(fm.getDateFormat() + " is invalid date format for the field " + fm);
            }
        }
    }

}
