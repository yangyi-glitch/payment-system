package utils;

import lombok.Data;

@Data
public class ResultUtils<T> {
    private T data;
    private Long total;
    private int code;

    public ResultUtils(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public ResultUtils(T data, Long total, int code) {
        this.data = data;
        this.total = total;
        this.code = code;
    }

    public static <T> ResultUtils success(T data) {
        return new ResultUtils<>(data, 200);
    }

    public static <T> ResultUtils success(T data, Long total) {
        return new ResultUtils<>(data, total, 200);
    }

    public static <T> ResultUtils error(T data) {
        return new ResultUtils<>(data, 500);
    }

    public static <T> ResultUtils withoutToken() {
        return new ResultUtils<>("请先登录~", 401);
    }
}
