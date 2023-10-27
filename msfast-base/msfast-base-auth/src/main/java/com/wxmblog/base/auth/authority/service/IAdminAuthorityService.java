package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.entity.LoginUser;

public interface IAdminAuthorityService<T extends LoginRequest> {

    LoginUser adminLogin(T loginRequest);

    void logout();
}
