package com.wxmblog.base.websocket.service;

import com.alibaba.fastjson.JSON;
import com.wxmblog.base.websocket.common.rest.request.UserSendMessage;
import com.wxmblog.base.websocket.netty.ChannelMap;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        Object object = redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (object != null) {
            UserSendMessage sendMessage = JSON.parseObject(object.toString(), UserSendMessage.class);
            Channel channel = ChannelMap.get(sendMessage.getId());
            if (channel != null) {
                channel.writeAndFlush(new TextWebSocketFrame(sendMessage.getContent()));
            }
        }
    }
}
