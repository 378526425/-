package com.wxmblog.base.websocket.common.rest.response;

import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-12 11:24
 **/

@Data
public class BaseMessageInfoResponse {

    private Integer sendUserId;

    private String content;

    private String messageFormat;

    private String sendName;

    private String sendPortrait;

    private String msgNo;


}
