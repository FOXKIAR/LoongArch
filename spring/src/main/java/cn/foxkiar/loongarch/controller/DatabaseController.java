package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.mapper.DatabaseMapper;
import cn.foxkiar.loongarch.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/database")
public class DatabaseController {
    final DatabaseMapper databaseMapper;

    public DatabaseController(DatabaseMapper databaseMapper) {
        this.databaseMapper = databaseMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<Result<List<String>>> getAllDb() {
        List<String> dbs = databaseMapper.getDatabases();
        dbs.remove("information_schema");
        dbs.remove("mysql");
        dbs.remove("performance_schema");
        dbs.remove("sys");
        // 删除系统架构
        return ResponseEntity.ok(Result.success(dbs));
    }

    @GetMapping("/all/{database}")
    public ResponseEntity<Result<List<String>>> getTables(@PathVariable String database) {
        return ResponseEntity.ok(Result.success(databaseMapper.getTables(database)));
    }

    @GetMapping("/struct/{database}/{table}")
    public ResponseEntity<Result<List<Map<String, Object>>>> getTables(@PathVariable String database, @PathVariable String table) {
        return ResponseEntity.ok(Result.success(databaseMapper.getTableStruct(database, table)));
    }
}
