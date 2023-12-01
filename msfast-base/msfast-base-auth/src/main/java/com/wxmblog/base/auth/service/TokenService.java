package com.wxmblog.base.auth.service;

import com.wxmblog.base.auth.common.rest.request.*;
import com.wxmblog.base.auth.common.rest.response.LoginUserResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface TokenService<T extends LoginRequest, R extends RegisterRequest> {

    void register(R request);

    LoginUserResponse login(T request);

    LoginUserResponse smsLogin(SmsLoginRequest request);

    void logout();

    void refreshToken(String token);

    void sendSms(SendSmsRequest sendSmsRequest);

    void checkSms(CheckSmsRequest checkSmsRequest);

    void wxAppletRegister(R request);

    LoginUserResponse wxAppletLogin(T request);

    LoginUserResponse adminLogin(T request);

    void authCheck(AuthCheckRequest request);

    String getAuthCode(String macAddressmac);

    String getMacAddress();
}
