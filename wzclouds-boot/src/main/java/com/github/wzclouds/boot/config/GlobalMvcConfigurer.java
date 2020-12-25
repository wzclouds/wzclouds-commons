package com.github.wzclouds.boot.config;

import com.github.wzclouds.boot.interceptor.HeaderThreadLocalInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 公共配置类, 一些公共工具配置
 *
 * @author wzclouds
 * @date 2018/8/25
 */
@AllArgsConstructor
public class GlobalMvcConfigurer implements WebMvcConfigurer {

    /**
     * 注册 拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HeaderThreadLocalInterceptor())
                .addPathPatterns("/**")
                .order(-20);
    }
}
