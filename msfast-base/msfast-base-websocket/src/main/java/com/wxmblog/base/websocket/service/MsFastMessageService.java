package com.wxmblog.base.websocket.service;

import com.wxmblog.base.common.utils.PageResult;
import com.wxmblog.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxmblog.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxmblog.base.websocket.common.rest.response.MessageListResponse;


public interface MsFastMessageService {

    PageResult<MessageInfoResponse> getMessageInfoRange(Integer sendUserId, Integer pageIndex, Integer pageSize);

    PageResult<MessageListResponse> getMessageListRange(Integer pageIndex, Integer pageSize);

    void deleteList(Integer sendUserId);

    Integer unRead();

    void addMessageList(BaseMessageInfo messageInfo, Integer userId, Integer sendUserId);
}
