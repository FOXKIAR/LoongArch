package cn.foxkiar.support.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private boolean status;
    private String message;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, "成功", data);
    }

    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    public static Result fail() {
        return new Result(false, "失败", null);
    }

    public static Result fail(String message) {
        return new Result(false, message, null);
    }

    public static Result fail(String message, Object data) {
        return new Result(false, message, data);
    }

    public String toJsonString() {
        return "{" +
                "\"result\":" + this.status + "," +
                "\"message\":\"" + this.message + "\"," +
                "\"data\":" + this.data +
                "}";
    }
}
