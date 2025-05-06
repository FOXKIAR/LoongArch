package cn.foxkiar.loongarch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*") // 允许所有域的请求
        .allowedMethods("POST", "GET", "PUT", "DELETE") // 允许的方法
        .allowedHeaders("*") // 允许的头部设置
        .maxAge(3600); // 预检间隔时间
        registry.addMapping("/user/login")
        .allowCredentials(true); // 是否发送cookie
    }
}