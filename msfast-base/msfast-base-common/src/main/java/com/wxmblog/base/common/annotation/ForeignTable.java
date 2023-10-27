package com.wxmblog.base.common.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ForeignTable {

    String value() default "";

    String message() default "外键数据不存在";
}
