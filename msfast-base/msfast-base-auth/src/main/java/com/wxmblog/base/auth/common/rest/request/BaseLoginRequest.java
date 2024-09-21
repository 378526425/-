package com.wxmblog.base.auth.common.rest.request;

import com.wxmblog.base.auth.common.enums.LoginType;
import lombok.Data;

@Data
public class BaseLoginRequest {

    private LoginType loginType;
}
