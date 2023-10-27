package com.wxmblog.base.common.rest.response;

import lombok.Data;

import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-06 15:19
 **/

@Data
public class BaseUserInfo {

    private Map<String, Object> extra;
}
