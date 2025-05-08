package cn.foxkiar.loongarch.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>("成功", data);
    }

    public static <T> Result<T> message(String msg) {
        return new Result<>(msg, null);
    }
}

