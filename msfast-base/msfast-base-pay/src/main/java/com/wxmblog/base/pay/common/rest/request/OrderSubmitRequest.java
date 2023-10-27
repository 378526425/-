package com.wxmblog.base.pay.common.rest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderSubmitRequest {

    @ApiModelProperty(value = "微信code")
    @NotBlank
    private String code;
}
