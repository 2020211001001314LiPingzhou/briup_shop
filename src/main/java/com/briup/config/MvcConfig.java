package com.briup.config;

import com.briup.web.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(
                        "/user/toLogin",
                        "/user/login",
                        "/user/toRegister",
                        "/user/register",
                        "/","/index","toIndex",
                        "/error",
                        "/images/**",
                        "/css/**",
                        "/js/**",
                        "/fonts/**"
                );
    }
}
