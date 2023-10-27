package com.wxmblog.base.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author ruoyi
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "alisms")
public class AliSmsConfig {

    private String accessKeyId;

    private String secret;

    private String signName;

    private String loginTemplateCode;

    private String registerTemplateCode;

    private String resetPasswordTemplateCode;

    /**
     * @Description: 默认60s 过期
     */
    @Value("${alisms.timeout:60}")
    private Long timeout;
}
