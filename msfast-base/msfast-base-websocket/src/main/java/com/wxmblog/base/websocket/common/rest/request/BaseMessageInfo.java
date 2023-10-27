package com.wxmblog.base.websocket.common.rest.request;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-11 14:39
 **/

@Data
public class BaseMessageInfo {

    private Integer sendUserId;

    private Integer acceptUserId;

    private String content;

    private String messageFormat;

    private String sendName;

    private String sendPortrait;

}
