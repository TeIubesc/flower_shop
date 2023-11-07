package lz.flower.utils;

import lombok.Data;

/**
 * 自定义异常处理器
 *
 * @Author liuzhi
 * @Date 2023/3/25 20:44
 */

@Data
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public static MyException error(Integer code, String message) {
        return new MyException(code, message);
    }
}
