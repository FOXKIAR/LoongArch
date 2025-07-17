package cn.foxkiar.loongarch.config;

import cn.foxkiar.loongarch.interceptor.GlobalInterceptor;
import cn.foxkiar.loongarch.mapper.RequestLogMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    final RequestLogMapper requestLogMapper;

    public WebMvcConfig(RequestLogMapper requestLogMapper) {
        this.requestLogMapper = requestLogMapper;
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor(requestLogMapper)).addPathPatterns("/**");
    }
}