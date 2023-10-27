package com.wxmblog.base.auth.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 16:48
 **/
@Data
public class LoginUserResponse<T> {

    private String token;

    private T info;
}
