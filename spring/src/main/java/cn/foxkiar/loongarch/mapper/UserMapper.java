package cn.foxkiar.loongarch.mapper;

import cn.foxkiar.loongarch.entity.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Person> {
}
