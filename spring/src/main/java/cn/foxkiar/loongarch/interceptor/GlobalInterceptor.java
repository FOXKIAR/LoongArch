package cn.foxkiar.loongarch.interceptor;

import cn.foxkiar.loongarch.entity.OperationLog;
import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.mapper.OperationLogMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalInterceptor implements HandlerInterceptor {
    final OperationLogMapper requestLogMapper;

    public GlobalInterceptor(OperationLogMapper requestLogMapper) {
        this.requestLogMapper = requestLogMapper;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HttpSession session = request.getSession(false);
        Person currentPerson =  session == null ? null : (Person) session.getAttribute("LOGIN_USER");
        Integer userId = currentPerson == null ? null : currentPerson.getId();
        String username = currentPerson == null ? null : currentPerson.getName();
        OperationLog operationLog;
        operationLog = new OperationLog(
                null,
                null,
                userId,
                username,
                request.getRequestURI(),
                request.getMethod(),
                response.getStatus() == 200
        );
        requestLogMapper.insert(operationLog);
    }
}
