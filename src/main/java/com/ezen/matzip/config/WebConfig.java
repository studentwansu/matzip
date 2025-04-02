package com.ezen.matzip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/review/**")
                .addResourceLocations("file:///C:/matzip-storage/img/review/");
    }
}

//CORS 정책 설정 (프론트엔드 접근 허용)
