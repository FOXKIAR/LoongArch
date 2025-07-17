package cn.foxkiar.loongarch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;

@Data
@NoArgsConstructor
public class RequestLog {
    private Time occurrence_time;
    private Method method;
    private String path;
    private Status response;
    private String failure_reason;

    public static RequestLog create(HttpServletRequest request, HttpServletResponse response) {
        RequestLog requestLog = new RequestLog();
        switch (request.getMethod()) {
            case "GET":
                requestLog.method = Method.GET;
                break;
                case "POST":
                    requestLog.method = Method.POST;
                    break;
                    case "PUT":
                        requestLog.method = Method.PUT;
                        break;
                        case "DELETE":
                            requestLog.method = Method.DELETE;
                            break;
                            default:
                                break;
        }
        requestLog.path = request.getRequestURI();
        requestLog.response = response.getStatus() == 200 ? Status.SUCCESS : Status.FAILURE;
        requestLog.failure_reason = "";
        return requestLog;
    }
}

enum Method {
    GET, POST, PUT, DELETE
}

enum Status {
    SUCCESS, FAILURE
}
