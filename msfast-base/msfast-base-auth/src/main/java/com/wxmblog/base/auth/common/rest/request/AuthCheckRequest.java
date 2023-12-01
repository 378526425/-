package com.wxmblog.base.auth.common.rest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wang
 * @date 2023/11/30 16:58
 */
@Data
public class AuthCheckRequest {

    @ApiModelProperty("mac地址")
    @NotBlank
    private String macAddressmac;

    @ApiModelProperty("授权码")
    @NotBlank
    private String authorizationCode;
}
