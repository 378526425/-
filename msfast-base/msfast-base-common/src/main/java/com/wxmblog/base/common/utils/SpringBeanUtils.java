package com.wxmblog.base.common.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringBeanUtils.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return context.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}

