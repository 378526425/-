package com.wxmblog.base.role.service;

import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.entity.LoginUser;

public interface IAdminLoginService<T extends LoginRequest> {

    LoginUser adminLogin(T loginRequest);

    void logout();
}
