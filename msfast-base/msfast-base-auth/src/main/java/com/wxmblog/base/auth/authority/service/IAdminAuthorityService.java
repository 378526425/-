package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.rest.request.LoginRequest;
import com.wxmblog.base.common.entity.LoginUser;

public abstract class IAdminAuthorityService<T extends LoginRequest> {

    public abstract LoginUser adminLogin(T loginRequest);

    public abstract void logout();
}
