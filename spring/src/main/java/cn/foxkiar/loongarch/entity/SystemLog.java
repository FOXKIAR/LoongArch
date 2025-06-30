package cn.foxkiar.loongarch.entity;

import lombok.Data;

import java.sql.Time;

@Data
public class SystemLog {
    private Time occurrence_time;
    private Level level;
    private String message;
    private String reason;
}

enum Level {
    INFO, WARN, ERROR
}