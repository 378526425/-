package com.wxmblog.base.common.service.impl;

import com.wxmblog.base.common.service.BaseCommonService;
import com.wxmblog.base.common.service.RedisService;
import com.wxmblog.base.common.constant.Constants;
import com.wxmblog.base.common.rest.response.BaseUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-07 11:09
 **/

@Service
public class BaseCommonServiceImpl implements BaseCommonService {

    @Autowired
    RedisService redisService;

    @Override
    public void updateUser(Integer userId, Map<String, Object> map) {

        BaseUserInfo baseUserInfo = redisService.getCacheObject(Constants.BASE_USER_INFO + userId);

        if (baseUserInfo == null) {
            baseUserInfo = new BaseUserInfo();
            baseUserInfo.setExtra(map);
        } else {

            if (baseUserInfo.getExtra() == null) {
                baseUserInfo.setExtra(map);
            } else {
                if (map != null) {
                    Map<String, Object> oldMap = baseUserInfo.getExtra();
                    map.keySet().forEach(key -> {
                        oldMap.put(key, map.get(key));
                    });
                    baseUserInfo.setExtra(oldMap);
                }
            }

        }

        redisService.setCacheObject(Constants.BASE_USER_INFO + userId, baseUserInfo);

    }
}
