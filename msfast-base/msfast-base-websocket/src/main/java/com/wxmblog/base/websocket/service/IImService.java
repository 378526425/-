package com.wxmblog.base.websocket.service;

import com.wxmblog.base.websocket.common.rest.response.ImUserInfoResponse;

public interface IImService {

    ImUserInfoResponse getImUser(Integer userId);
}
