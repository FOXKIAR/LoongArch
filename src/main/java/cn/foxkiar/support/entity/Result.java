package cn.foxkiar.support.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result<T> success(T data) {
        this.code = 200;
        this.message = "成功";
        this.data = data;
        return this;
    }

    public Result<T> success(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public Result<T> fail(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        return this;
    }

    public Result<T> fail(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = null;
        return this;
    }
}
