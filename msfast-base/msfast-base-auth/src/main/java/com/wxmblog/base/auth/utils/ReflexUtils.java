package com.wxmblog.base.auth.utils;

import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.auth.common.rest.request.RegisterRequest;
import com.wxmblog.base.auth.authority.service.IAuthorityService;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-22 16:29
 **/

public class ReflexUtils {

    public static Class<? extends LoginRequest> getServiceViewModel(IAuthorityService service) {
        Class clazz = service.getClass();
        while (clazz.getSimpleName().contains("$")) {
            clazz = (Class) clazz.getGenericSuperclass();
        }

        Class<? extends LoginRequest> clsViewModel = getParameterizedType(clazz, IAuthorityService.class, LoginRequest.class);

        return clsViewModel;
    }

    public static <T> Class<? extends RegisterRequest> getServiceView(IAuthorityService service) {
        Class clazz = service.getClass();
        while (clazz.getSimpleName().contains("$")) {
            clazz = (Class) clazz.getGenericSuperclass();
        }

        Class<? extends RegisterRequest> clsViewModel = getParameterizedType(clazz, IAuthorityService.class, RegisterRequest.class);

        return clsViewModel;
    }

    public static <T, S> Class<T> getParameterizedType(Class<? extends S> clazz, Class<S> superClass, Class<T> argType) {
        Class<T> result = null;
        result = getInterfaceParameterizedType(clazz, superClass, argType);
        if (result != null) return result;

        result = getSuperClassParameterizedType(clazz, superClass, argType);

        if (result == null) throw new JrsfException(BaseExceptionEnum.UNKNOWN_EXCEPTION);

        return result;

    }

    public static <T, S> Class<T> getInterfaceParameterizedType(Class<? extends S> clazz, Class<S> superClass, Class<T> argType) {
        Type[] types = clazz.getGenericInterfaces();
        if (types.length > 0) {
            for (Type tp : types) {
                return getModelClass(superClass, argType, tp);
            }
        }
        return null;
    }

    public static <T, S> Class<T> getSuperClassParameterizedType(Class<? extends S> clazz, Class<S> superClass, Class<T> argType) {
        Object obj = clazz.getGenericSuperclass();

        while (!(obj instanceof ParameterizedType)) {
            Class cls = (Class) obj;
            Class<T> clsReturn = getInterfaceParameterizedType(cls, superClass, argType);
            if (clsReturn != null) return clsReturn;

            if (cls.getGenericSuperclass() == null) break;
            obj = cls.getGenericSuperclass();
        }

        if (obj instanceof ParameterizedType) {
            return getModelClass(superClass, argType, (Type) obj);
        }

        return null;
    }

    private static <T, S> Class<T> getModelClass(Class<S> superClass, Class<T> argType, Type tp) {
        ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) tp;
        if (superClass.isAssignableFrom(parameterizedType.getRawType())) {

            Type[] argumentTypes = parameterizedType.getActualTypeArguments();
            for (Type argumentType : argumentTypes) {

                if (argType.isAssignableFrom((Class) argumentType)) {
                    return (Class<T>) argumentType;
                }
            }
        }
        return null;
    }

}
