package com.afrunt.jach.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Andrii Frunt
 */
@Target(METHOD)
@Retention(RUNTIME)
@Inherited
public @interface ACHField {

    String EMPTY_DATE_PATTERN = "";

    int start() default 0;

    int length() default 0;

    InclusionRequirement inclusion() default InclusionRequirement.OPTIONAL;

    String name();

    String[] values() default {};

    boolean typeTag() default false;

    String dateFormat() default EMPTY_DATE_PATTERN;
}
