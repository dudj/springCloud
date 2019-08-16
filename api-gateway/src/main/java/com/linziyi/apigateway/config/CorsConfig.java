package com.linziyi.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * cors Cross Origin Resource Sharing
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);//cookie是否跨域
        config.setAllowedOrigins(Arrays.asList("*"));//原始域 域名(http://www.a.com)
        config.setAllowedHeaders(Arrays.asList("*"));//原始头
        config.setAllowedMethods(Arrays.asList("*"));//请求方式
        config.setMaxAge(300l);//缓存时间
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
