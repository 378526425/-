package com.wxmblog.base.websocket.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-13 17:09
 **/

@Data
public class MessageInfoResponse {

    private Integer sendUserId;

    private Integer acceptUserId;

    private String content;

    private String messageFormat;

    private String msgNo;

    private Boolean self;

    private String nickName;

    private String headPortrait;

}
