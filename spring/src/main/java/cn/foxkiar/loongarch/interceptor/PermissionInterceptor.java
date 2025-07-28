package cn.foxkiar.loongarch.interceptor;

import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.exception.AuthenticationException;
import cn.foxkiar.loongarch.exception.NoPermissionException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException, NoPermissionException {
        if (request.getMethod().equals("GET"))
            return true;
        HttpSession session = request.getSession(false);
        Person currentPerson =  session == null ? null : (Person) session.getAttribute("LOGIN_USER");
        if (currentPerson == null || currentPerson.getPermission() == null)
            throw new AuthenticationException();
        int requiredPermission = switch (request.getMethod()) {
            case "POST" -> 0b001;
            case "PUT" -> 0b010;
            case "DELETE" -> 0b100;
            default -> 0b1000;
        };
        if ((currentPerson.getPermission() & requiredPermission) == requiredPermission)
            return true;
        else
            throw new NoPermissionException();
    }
}
