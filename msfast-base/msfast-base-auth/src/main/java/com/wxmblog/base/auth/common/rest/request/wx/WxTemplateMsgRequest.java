package com.wxmblog.base.auth.common.rest.request.wx;

import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-08-17 15:49
 **/

@Data
public class WxTemplateMsgRequest {

    /**
     * @Description: 所需下发的订阅模板id 必填
     */
    private String templateId;

    /**
     * @Description: 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转  非必填
     */
    private String page;

    /**
     * @Description: 接收者（用户）的 openid 是
     */
    private String touser;

    /**
     * @Description: 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }的object 是
     */
    private List<WxTemplateData> data;

    /**
     * @Description: 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版 是
     */
    private String miniprogramState="formal";

    /**
     * @Description: 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN 是
     */
    private String lang="zh_CN";
}
