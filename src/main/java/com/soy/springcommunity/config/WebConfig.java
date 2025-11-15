package com.soy.springcommunity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.soy.springcommunity.utils.ConstantUtil.UPLOAD_USERS_DIR;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("file:" + UPLOAD_USERS_DIR);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + UPLOAD_USERS_DIR)          // 실제 파일 경로
                .setCachePeriod(60 * 60 * 24 * 30);     // 30일 캐시
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}