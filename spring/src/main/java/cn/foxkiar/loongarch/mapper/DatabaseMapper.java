package cn.foxkiar.loongarch.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DatabaseMapper {
    @Select("SHOW DATABASES")
    List<String> getDatabases();

    @Select("SHOW TABLES FROM ${dbName}")
    List<String> getTables(@Param("dbName") String dbName);

    @Select("SHOW FULL COLUMNS FROM ${dbName}.${tableName}")
    List<Map<String, Object>> getTableStruct(@Param("dbName") String dbName, @Param("tableName") String tableName);
}
