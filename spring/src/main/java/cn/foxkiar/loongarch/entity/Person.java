package cn.foxkiar.loongarch.entity;

import cn.foxkiar.loongarch.validation.Groups;
import cn.foxkiar.loongarch.validation.Phone;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class Person {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "姓名不能为空", groups = {Groups.Save.class})
    private String name;
    @NotBlank(message = "账号不能为空", groups = {Groups.Login.class, Groups.Save.class})
    @Length(min = 8, max = 16, message = "账号的长度应该在8到16字符之间", groups = {Groups.Login.class, Groups.Save.class})
    private String account;
    @TableField(select = false)
    @NotBlank(message = "密码不能为空", groups = {Groups.Login.class})
    @Length(min = 8, max = 16, message = "密码的长度应该在8到16字符之间", groups = {Groups.Login.class})
    private String password;
    private Integer permission;
    @Email(groups = {Groups.Save.class})
    private String email;
    @Phone(groups = {Groups.Save.class})
    private String phone;
}
