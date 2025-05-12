package cn.foxkiar.loongarch.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("loong_arch.patrol")
public class Patrol {
    @TableId()
    private Date recordDate;
    private Integer userId;
    private String userName;
    private Boolean isNormal;
    private String comment;

    @TableField(exist = false)
    private Long startDate;
    @TableField(exist = false)
    private Long endDate;
}
