package com.ezen.matzip.config;

import com.ezen.matzip.domain.user.controller.StoreInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private StoreInterceptor storeInterceptor;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/review/**")
//                .addResourceLocations("file:///C:/matzip-storage/img/review/");
//
//        registry.addResourceHandler("/img/restaurant/**")
//                .addResourceLocations("file:C:/dev/img/restaurant");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기본 정적 리소스 폴더(src/main/resources/static)로 매핑
        registry.addResourceHandler("/img/review/**")
                .addResourceLocations("classpath:/static/img/review/");

        registry.addResourceHandler("/img/restaurant/**")
                .addResourceLocations("classpath:/static/img/restaurant/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(storeInterceptor)
                .addPathPatterns("/business/**");
    }

}

//CORS 정책 설정 (프론트엔드 접근 허용)
