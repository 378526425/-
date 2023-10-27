package com.wxmblog.base.common.entity;

import com.wxmblog.base.common.enums.BaseUserTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 16:48
 **/
@Data
public class LoginUser<T> {

    private Integer id;

    private BaseUserTypeEnum userType;

    private T info;
}
