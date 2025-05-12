package cn.foxkiar.loongarch.exception;

import cn.foxkiar.loongarch.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static cn.foxkiar.loongarch.util.Result.message;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMediaTypeException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Result<Exception>> requestFormatError() {
        return ResponseEntity.badRequest().body(message("参数格式错误"));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Result<Exception>> databaseConnectError(SQLException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message("数据库连接失败"));
    }
}
