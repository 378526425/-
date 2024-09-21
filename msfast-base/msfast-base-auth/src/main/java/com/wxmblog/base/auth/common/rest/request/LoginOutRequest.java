package com.wxmblog.base.auth.common.rest.request;

import com.wxmblog.base.auth.common.validtype.AdminLogin;
import com.wxmblog.base.auth.common.validtype.PhoneLogin;
import com.wxmblog.base.auth.common.validtype.WxAppletLogin;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@Data
public class LoginOutRequest extends LoginRequest{

}
