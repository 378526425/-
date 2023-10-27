package com.wxmblog.base.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-18 18:14
 **/

@Component
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private int timeout = 3000;

    private String address;

    private String password;

    private Integer database;

    private int connectionPoolSize = 128;

    private int connectionMinimumIdleSize = 24;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private int subscriptionConnectionMinimumIdleSize = 8;

    private int subscriptionConnectionPoolSize = 128;

    private int subscriptionsPerConnection = 20;

    private String[] sentinelAddresses;

    private String masterName;
}
