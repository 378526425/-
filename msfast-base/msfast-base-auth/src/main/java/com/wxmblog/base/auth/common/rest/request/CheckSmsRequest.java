package com.wxmblog.base.auth.common.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-27 16:12
 **/

@Data
public class CheckSmsRequest extends SendSmsRequest {

    @NotBlank
    private String code;
}
