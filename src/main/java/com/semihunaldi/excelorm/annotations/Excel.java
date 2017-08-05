package com.semihunaldi.excelorm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

    int sheet() default 0;

    int firstRow() default 1;

    int firstCol() default 0;
}