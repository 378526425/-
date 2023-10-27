package com.wxmblog.base.common.utils;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Set;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-27 11:33
 **/

public class MapUtils {

    public static <T> void copyPropertiesInclude(Map<String, Object> updateProperties, T bean) {

        if (ObjectUtil.isNotNull(updateProperties)) {
            Set<Map.Entry<String, Object>> revisabilityFiledSet = updateProperties.entrySet();
            for (Map.Entry<String, Object> entry : revisabilityFiledSet) {
                Object value = entry.getValue();
                if (value != null) {
                    try {
                        BeanUtils.setProperty(bean, entry.getKey(), value);
                    } catch (Exception e) {
                    }
                }
            }
        }

    }
}
