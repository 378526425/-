package com.wxmblog.base.role.common.rest.response;

import com.wxmblog.base.common.enums.BaseUserTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-08-29 17:25
 **/

@Data
public class LoginResponse {

    private Integer id;

    private BaseUserTypeEnum userType;
}
