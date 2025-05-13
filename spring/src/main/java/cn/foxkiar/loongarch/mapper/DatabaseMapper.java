package cn.foxkiar.loongarch.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DatabaseMapper {
    @Select("SHOW DATABASES")
    List<String> getAllDBNames();

    @Select("SHOW TABLES FROM #{dbName}")
    List<String> getAllTableNames(@Param("dbName") String dbName);
}
