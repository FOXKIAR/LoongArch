package cn.foxkiar.loongarch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;

@Data
public class SystemLog {
    @TableId(type = IdType.INPUT)
    private Time occurrence_time;
    private LevelEnum level;
    private String message;
    private String reason;
}

enum LevelEnum {
    INFO, WARN, ERROR
}