package cn.foxkiar.support.exception;

import cn.foxkiar.support.entity.Result;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value =
    {TokenExpiredException.class, SignatureVerificationException.class, JWTDecodeException.class})
    public ResponseEntity<Result> tokenVerify(Exception ignoredE) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.fail("无效token，请重新登录"));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> all(Exception e) {
        log.error("全局异常捕获 {} >>>{}", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.fail(e.getMessage()));
    }
}