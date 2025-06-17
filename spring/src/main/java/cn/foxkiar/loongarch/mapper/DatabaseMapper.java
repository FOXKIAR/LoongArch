package cn.foxkiar.loongarch.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DatabaseMapper {
    @Select("SELECT tablename FROM pg_tables WHERE schemaname = 'public'")
    List<String> getTables();

    @Select("select * from information_schema.columns\n" +
            "where table_name = #{tableName};")
    List<Map<String, Object>> getTableStruct(@Param("tableName") String tableName);
}
