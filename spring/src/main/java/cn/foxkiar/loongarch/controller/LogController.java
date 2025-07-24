package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.OperationLog;
import cn.foxkiar.loongarch.mapper.OperationLogMapper;
import cn.foxkiar.loongarch.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    final OperationLogMapper operationLogMapper;

    public LogController(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    @GetMapping("/operation/page/{currentPage}")
    public ResponseEntity<Result<Page<OperationLog>>> getOperationLog(@PathVariable int currentPage) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(OperationLog::getId);
        return ResponseEntity.ok(Result.success(operationLogMapper.selectPage(new Page<>(currentPage, 10), wrapper)));
    }
}
