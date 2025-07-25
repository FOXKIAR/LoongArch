package cn.foxkiar.loongarch.interceptor;

import cn.foxkiar.loongarch.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException {
        log.info("进入拦截器");
        log.error("进入拦截器");
        if (request.getMethod().equals("GET"))
            return true;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null)
            throw new AuthenticationException();
        else
            return true;
    }
}
