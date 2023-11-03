package com.group2.cafejava.config;

import com.group2.cafejava.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DemoWebConfig implements WebMvcConfigurer {


    /**
     * interceptor configuration
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add interceptor
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/**")
                // Exclude path for interceptor
                .excludePathPatterns("/api/loginUser", "/api/loginStaff", "/api/user/**", "/api/menu/**","/api/cart/**");
    }


}
