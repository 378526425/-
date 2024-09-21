package com.wxmblog.base.auth.authority.service;

import com.wxmblog.base.auth.common.enums.LoginType;
import com.wxmblog.base.auth.common.rest.request.*;
import com.wxmblog.base.common.entity.LoginUser;
import lombok.Data;

/*
 * @Author
 * @Description  有关权限相关的业务代码实现
 * @Date 21:45 2022/6/18
 **/
@Data
public  abstract class IAuthorityService<T extends BaseLoginRequest, R extends RegisterRequest> {

    private LoginType loginType;

    public abstract void register(R registerRequest);

    public abstract LoginUser login(T loginRequest);

    public abstract LoginUser smsLogin(SmsLoginRequest loginRequest);

    public abstract void logout();

    public abstract  void sendSmsBefore(SendSmsRequest sendSmsRequest);

    public abstract void wxAppletRegister(R request);
}
