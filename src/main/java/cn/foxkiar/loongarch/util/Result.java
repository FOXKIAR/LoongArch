package cn.foxkiar.loongarch.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Message msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(Message.SUCCESS, data);
    }

    public static <T> Result<T> message(Message msg) {
        return new Result<>(msg, null);
    }

    public enum Message {
        SUCCESS,
        ID_NOT_FOUND,
        INCORRECT_USERNAME_OR_PASSWORD,
        PARAMETET_FORMAT_ERROR,
        INTERNAL_SERVER_ERROR,
    }
}

