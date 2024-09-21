package com.wxmblog.base.auth.common.rest.request;

import com.wxmblog.base.auth.common.enums.MessageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-23 15:31
 **/

@Data
public class SendSmsRequest extends BaseLoginRequest{

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确")
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull
    @ApiModelProperty("短信类型 LOGIN(\"登陆\"),\n" +
            "    REGISTER(\"注册\"),\n" +
            "    RESETPWD(\"重置密码\")")
    private MessageType messageType;
}
