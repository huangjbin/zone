package com.zone.quartz_module.config;

import com.zone.quartz_module.interceptors.UrlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 自己定义的拦截器类
     * @return
     */
    @Bean
    UrlInterceptor sysUserLoginInterceptor() {
        return new UrlInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysUserLoginInterceptor());
    }

}