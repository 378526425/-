package com.wxmblog.base.auth.service;

import org.springframework.scheduling.annotation.Async;

public interface AuthorizeVerifyService {

    @Async
    void AuthorizeVerify();
}
