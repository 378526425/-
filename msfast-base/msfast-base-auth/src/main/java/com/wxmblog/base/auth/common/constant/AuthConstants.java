package com.wxmblog.base.auth.common.constant;

import com.alibaba.fastjson.JSON;
import com.wxmblog.base.auth.common.rest.request.AuthCheckRequest;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.utils.CPUUtils;
import com.wxmblog.base.common.utils.SpringBeanUtils;
import com.wxmblog.base.common.web.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * @author wang
 * @date 2023/11/29 14:06
 */
@Slf4j
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
            AuthCheckRequest request = new AuthCheckRequest();
            String cpuId = CPUUtils.getCpuId();
            log.info("authorizationKey:{}", cpuId);
            request.setAuthorizationKey(cpuId);
            request.setAuthorizationCode(ConfigConstants.AUTHORIZATION_CODE());
            log.info(JSON.toJSONString(request));
            result = restTemplate.postForObject("https://www.wxmblog.com/wxmapi/token/authCheck", request, R.class);
        } catch (Exception e) {

        }

    }


    public static final Boolean OPERATING = (result != null && result.getCode() == 200);

    public static final Long RUN_TIME = 3600 * 24 * 1000L;
}
