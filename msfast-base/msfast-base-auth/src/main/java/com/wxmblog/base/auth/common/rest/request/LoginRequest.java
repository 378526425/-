package com.wxmblog.base.auth.common.rest.request;

import com.wxmblog.base.auth.common.validtype.PhoneLogin;
import com.wxmblog.base.auth.common.validtype.AdminLogin;
import com.wxmblog.base.auth.common.validtype.WxAppletLogin;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author ruoyi
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(groups = {PhoneLogin.class, AdminLogin.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(groups = {PhoneLogin.class,AdminLogin.class})
    private String password;

    /**
     * @Description: 微信code
     */
    @NotBlank(groups = {WxAppletLogin.class})
    private String code;

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String openId; //

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String sessionKey;

    /*
     * @Description  登录方式为 WX_Applet 时
     **/
    private String unionId;

}
