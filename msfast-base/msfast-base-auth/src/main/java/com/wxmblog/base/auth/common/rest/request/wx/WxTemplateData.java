package com.wxmblog.base.auth.common.rest.request.wx;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-08-17 15:48
 **/

@Data
public class WxTemplateData {

    private String name;

    private String value;

    public WxTemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
