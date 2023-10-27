package com.wxmblog.base.auth.authority.service;

import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description: 业务权限相关校验
 * @author: Mr.Wang
 * @create: 2022-06-20 09:57
 **/

@Service
public class TokenValidServiceImpl implements TokenValidService {

    @Override
    public Boolean hasPermission() {
        return true;
    }
}
