package com.wxmblog.base.websocket.common.rest.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSendMessage implements Serializable {

    private Integer id;

    private String content;
}
