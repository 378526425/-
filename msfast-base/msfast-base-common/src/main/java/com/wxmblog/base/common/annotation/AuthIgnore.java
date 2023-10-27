package com.wxmblog.base.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: 允许匿名访问注解
 * @Author: Mr.Wang
 * @date 2022/6/17 下午5:00
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {
}
