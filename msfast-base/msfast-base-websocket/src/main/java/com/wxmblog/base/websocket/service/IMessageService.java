package com.wxmblog.base.websocket.service;

import com.wxmblog.base.websocket.common.rest.request.BaseMessageInfo;

public interface IMessageService {

    void send(BaseMessageInfo messageInfo);

}
