package com.wxmblog.base.auth.common.rest.request;

import com.wxmblog.base.auth.common.validtype.PhoneRegister;
import com.wxmblog.base.auth.common.validtype.WxAppletRegister;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
@Data
public class RegisterRequest extends BaseLoginRequest{

    @NotBlank(groups = {PhoneRegister.class})
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确", groups = {PhoneRegister.class})
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    @NotBlank(groups = {PhoneRegister.class})
    private String verificationCode;

    @NotBlank(message = "密码不可为空", groups = {PhoneRegister.class})
    @Length(min = 6, max = 25, message = "密码为{min}-{max}位", groups = {PhoneRegister.class})
    private String password;

    @NotBlank(message = "确认密码不可为空", groups = {PhoneRegister.class})
    @Length(min = 6, max = 25, message = "确认密码为{min}-{max}位", groups = {PhoneRegister.class})
    private String truePassword;

    @NotBlank(groups = {WxAppletRegister.class})
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
