package cn.foxkiar.loongarch.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("loong_arch.patrol")
public class Patrol {
    private Integer id;
    private Integer userId;
    private String userName;
    private Boolean isNormal;
    private String comment;
    private Date recordDate;
}
