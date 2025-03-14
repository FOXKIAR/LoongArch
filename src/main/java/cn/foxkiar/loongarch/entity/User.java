package cn.foxkiar.loongarch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName("loong_arch.user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 8, max = 16, message = "用户名的长度应该在8到16字符之间")
    private String username;
    @TableField(select = false)
    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码的长度应该在8到16字符之间")
    private String password;
    private Integer permission;
}
