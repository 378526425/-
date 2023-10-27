package com.wxmblog.base.websocket.common.rest.request;


import com.wxmblog.base.websocket.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-12 16:16
 **/

@Data
public class WebSocketMessage {

    private MessageTypeEnum messageType;

    private String info;
}
