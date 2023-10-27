package com.wxmblog.base.common.utils;

import com.wxmblog.base.common.constant.ConfigConstants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    /**
     * 获取请求token
     */
    public static String getToken() {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 从header获取token标识
        String token = request.getHeader(ConfigConstants.AUTHENTICATION());
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(ConfigConstants.PREFIX())) {
            token = token.replaceFirst(ConfigConstants.PREFIX(), "");
        }
        return token;
    }
}
