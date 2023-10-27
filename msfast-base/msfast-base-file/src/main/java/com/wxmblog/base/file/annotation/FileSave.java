package com.wxmblog.base.file.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Author wanglei
 * @Description  临时文件持久化
 * @Date 16:08 2022/11/13
 * @Param
 * @return
 **/
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSave {

    String table() default "";

    String field() default "";

    boolean tokenId() default false;

    boolean updateDelete() default true;
}
