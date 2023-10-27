package com.wxmblog.base.websocket.utils;

import com.alibaba.fastjson.JSON;
import com.wxmblog.base.websocket.common.constant.WebSocketConstants;
import com.wxmblog.base.websocket.common.rest.request.UserSendMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-13 17:29
 **/

@Component
public class ChannelUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendText(Channel channel, String text) {
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(text));
        }
    }

    public void sendText(Integer userId, String text) {
        UserSendMessage userSendMessage = new UserSendMessage();
        userSendMessage.setId(userId);
        userSendMessage.setContent(text);
        redisTemplate.convertAndSend(WebSocketConstants.REDIS_CHANNEL_MESSAGE, JSON.toJSONString(userSendMessage));
    }

    public String getMessageInfoKey(Integer sendUserId, Integer acceptUserId) {
        if (sendUserId != null && acceptUserId != null) {
            if (sendUserId < acceptUserId) {
                return sendUserId + WebSocketConstants.MSG_INFO + acceptUserId;
            } else {
                return acceptUserId + WebSocketConstants.MSG_INFO + sendUserId;
            }
        }
        return WebSocketConstants.MSG_INFO;
    }


}
