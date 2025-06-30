package cn.foxkiar.loongarch.entity;

import lombok.Data;

import java.sql.Time;

@Data
public class RequestLog {
    private Time occurrence_time;
    private Method method;
    private String path;
    private Status response;
    private String failure_reason;
}

enum Method {
    GET, POST, PUT, DELETE
}

enum Status {
    SUCCESS, FAILURE
}
