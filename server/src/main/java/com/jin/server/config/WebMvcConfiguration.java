package com.jin.server.config;

import com.jin.server.interceptor.JwtTokenAdminInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器，并放行 Knife4j 相关路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin/employee/login",      // 登录接口
                        "/doc.html",                  // Knife4j 文档页面
                        "/swagger-ui/**",             // SpringDoc UI 资源
                        "/v3/api-docs/**",            // API 规范接口
                        "/webjars/**"                 // 前端静态资源
                );
    }

    /**
     * 自定义 API 文档信息（可选）
     */
    @Bean
    public OpenAPI customOpenAPI() {
        log.info("初始化 API 文档信息");
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .version("2.0")
                        .description("苍穹外卖项目接口文档 (Spring Boot 3 + Knife4j)"));
    }
}