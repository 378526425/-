package com.wxmblog.base.auth.authority.interceptor;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置用户安全验证拦截类
 *
 * @author: wuhb
 * @date: 2019/2/25
 */
@Configuration
public class AuthorityInterceptorConfig implements WebMvcConfigurer {

    @RefreshScope
    @Bean
    public AuthorityInterceptor getUserInterceptor() {
        return new AuthorityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**");
    }
}
