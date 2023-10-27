package com.wxmblog.base.websocket.config;

import com.wxmblog.base.websocket.common.constant.WebSocketConstants;
import com.wxmblog.base.websocket.service.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisSubConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        //订阅频道redis.news 和 redis.life  这个container 可以添加多个 messageListener
        container.addMessageListener(listener, new ChannelTopic(WebSocketConstants.REDIS_CHANNEL_MESSAGE));
        return container;
    }
}
