package cn.foxkiar.loongarch.interceptor;

import cn.foxkiar.loongarch.entity.RequestLog;
import cn.foxkiar.loongarch.mapper.RequestLogMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalInterceptor implements HandlerInterceptor {
    final RequestLogMapper requestLogMapper;

    public GlobalInterceptor(RequestLogMapper requestLogMapper) {
        this.requestLogMapper = requestLogMapper;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        requestLogMapper.insert(RequestLog.create(request, response));
    }
}
