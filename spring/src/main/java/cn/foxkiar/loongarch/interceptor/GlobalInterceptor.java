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
        OperationLog operationLog;
        if (currentPerson != null)
            operationLog = new OperationLog(
                    null,
                    null,
                    currentPerson.getId(),
                    currentPerson.getName(),
                    request.getRequestURI(),
                    request.getMethod(),
                    response.getStatus() == 200
            );
        else
            operationLog = new OperationLog(
                    null,
                    null,
                    null,
                    null,
                    request.getRequestURI(),
                    request.getMethod(),
                    response.getStatus() == 200
            );
        requestLogMapper.insert(operationLog);
    }
}
