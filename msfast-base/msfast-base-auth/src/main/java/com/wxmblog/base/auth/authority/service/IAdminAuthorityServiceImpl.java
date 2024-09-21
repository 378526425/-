package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.entity.LoginUser;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-13 10:41
 **/

public class IAdminAuthorityServiceImpl<T extends LoginRequest> extends IAdminAuthorityService<T> {
    @Override
    public LoginUser adminLogin(T loginRequest) {
        return null;
    }

    @Override
    public void logout() {

    }
}
