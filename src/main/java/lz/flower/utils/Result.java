package lz.flower.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author liuzhi
 * @Date 2022/10/2 11:57
 * 返回结果的模型类，用于前后端的数据格式同一，也称为前后端数据协议
 */
@Data
@AllArgsConstructor
public class Result {
    private Integer code;
    private Boolean status;
    private Object data;
    private String message;

    Result() {
    }

    public Result(String msg) {
        this.message = msg;
    }

    public Result(Boolean status) {
        this.status = status;
    }

    public Result(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Boolean status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Boolean status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Result(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean status, Integer code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result success(Object data) {
        return new Result(true, data);
    }

    public static Result success(String message) {
        return new Result(true, message);
    }

    public static Result success(Object data, String message) {
        return new Result(true, data, message);
    }

    public static Result success(Integer code, String message) {
        return new Result(true, code, message);
    }

    public static Result success(Integer code, String message, Object data) {
        return new Result(true, code, message, data);
    }

    public static Result error() {
        return new Result(false, ResultCode.DATA_NOT_EXIST.code(), ResultCode.DATA_NOT_EXIST.message());
    }

    public static Result error(String message) {
        return new Result(false, message);
    }

    public static Result error(Integer code, String message) {
        return new Result(false, code, message);
    }

    public static Result error(Boolean status, String message) {
        return new Result(status, message);
    }

    public static Result error(Object data, String message) {
        return new Result(false, data, message);
    }

}
