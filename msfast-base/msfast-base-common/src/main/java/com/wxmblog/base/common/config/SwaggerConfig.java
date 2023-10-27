package com.wxmblog.base.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.wxmblog.base.common.constant.ParamTypeConstants;
import com.wxmblog.base.common.constant.ConfigConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-19 16:21
 **/
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .paths(PathSelectors.any())
                .build()
                .enable(ConfigConstants.ENABLE_SWAGGER())
                .globalOperationParameters(newArrayList(new ParameterBuilder()
                        .name(ConfigConstants.AUTHENTICATION())
                        .description("token")
                        .modelRef(new ModelRef("string"))
                        .parameterType(ParamTypeConstants.requestHeader)
                        .required(false)
                        .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("msfast开源")
                .description("msfast文档")
                .termsOfServiceUrl("https://www.wxmblog.com/")
                .version("3.0.0")
                .build();
    }
}
