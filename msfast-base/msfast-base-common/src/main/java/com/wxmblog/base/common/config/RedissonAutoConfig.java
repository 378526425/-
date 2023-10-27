package com.wxmblog.base.common.config;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-18 18:14
 **/

import com.wxmblog.base.common.properties.RedissonProperties;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 自动配置类
 */
@Configuration
public class RedissonAutoConfig {

    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setDatabase(redssionProperties.getDatabase())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize())
                .setSubscriptionConnectionMinimumIdleSize(redssionProperties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redssionProperties.getSubscriptionConnectionPoolSize())
                .setSubscriptionsPerConnection(redssionProperties.getSubscriptionsPerConnection())
                ;

        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
