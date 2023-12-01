package com.wxmblog.base.auth.common.constant;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.wxmblog.base.auth.common.rest.request.AuthCheckRequest;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.utils.SM4Util;
import com.wxmblog.base.common.utils.SpringBeanUtils;
import com.wxmblog.base.common.web.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wang
 * @date 2023/11/29 14:06
 */
public class AuthConstants {

    /**
     * @Description: 应用启动时间
     * @Author: wang
     * @Date: 2023/11/29
     */
    public static final Long START_TINE = System.currentTimeMillis();

    public static R result = null;

    static {
        try {
            RestTemplate restTemplate = SpringBeanUtils.getBean(RestTemplate.class);
            InetAddress inetAddress = null;
            inetAddress = InetAddress.getLocalHost();
            AuthCheckRequest request = new AuthCheckRequest();
            String localMacAddress = NetUtil.getMacAddress(inetAddress);
            request.setMacAddressmac(localMacAddress);
            request.setAuthorizationCode(ConfigConstants.AUTHORIZATION_CODE());
            result = restTemplate.postForObject("https://www.wxmblog.com/wxmapi/token/authCheck", request, R.class);
        } catch (Exception e) {

        }

    }


    public static final Boolean OPERATING = (result != null && result.getCode() == 200);

    public static final Long RUN_TIME = 3600*24 * 1000L;
}
