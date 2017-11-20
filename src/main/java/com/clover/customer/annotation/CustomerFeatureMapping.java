package com.clover.customer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerFeatureMapping {

    String fieldName() default "";
    /**
     * if need update to other fields, if not need, just update to field with same name;
     *
     */
    String targetField() default "";

    String categoryCode() default "";
}
