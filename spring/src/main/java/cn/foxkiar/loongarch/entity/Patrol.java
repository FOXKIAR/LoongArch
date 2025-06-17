package cn.foxkiar.loongarch.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class Patrol {
    @TableId(type = IdType.INPUT)
    private Date recordDate;
    private Integer personId;
    private String personName;
    private Boolean isNormal;
    private String comment;

    @TableField(exist = false)
    private Long startDate;
    @TableField(exist = false)
    private Long endDate;
}
