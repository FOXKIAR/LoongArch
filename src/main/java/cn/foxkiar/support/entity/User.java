package cn.foxkiar.support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("support_datahub.sd_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String account;
    @TableField(exist = false)
    private String password;
    @TableField(select = false)
    private String passwordHash;
    @TableField(exist = false)
    private Boolean isKeepLogged;
    private String phone;
    private String email;
    private Boolean isAdmin;
}
