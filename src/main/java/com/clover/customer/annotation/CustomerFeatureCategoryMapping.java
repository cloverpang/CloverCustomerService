package com.clover.customer.annotation;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerFeatureCategoryMapping {
    String categoryName() default "";
    String categoryCode() default "";
}
