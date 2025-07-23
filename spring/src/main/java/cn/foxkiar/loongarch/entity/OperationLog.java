package cn.foxkiar.loongarch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {
    @TableId(type = IdType.AUTO)
    public Long id;
    public Timestamp completion_time;
    public Integer operator_id;
    public String operator_name;
    public String module;
    public String operate;
    public Boolean result;


}