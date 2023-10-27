package com.wxmblog.base.file.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author ruoyi
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "minio")
public class MinioConfig
{
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accesskey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
    * @Description: url
    */
    private String url;

    @Bean
    public MinioClient getMinioClient()
    {
        return MinioClient.builder().endpoint(endpoint).credentials(accesskey, secretKey).build();
    }
}
