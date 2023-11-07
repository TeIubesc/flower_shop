package lz.flower.utils;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

/**
 * @Author liuzhi
 * @Date 2022/10/5 15:50
 * 自定义异常处理器
 * @RestControllerAdvice注解将作用在所有注解了@RequestMapping的控制器的方法上
 * @ExceptionHandler：用于指定异常处理方法。当与@RestControllerAdvice配合使用时，用于全局处理控制器里的异常。
 */
@RestControllerAdvice
public class ExceptionHandling {

    //不支持该http请求方式异常
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Result httpRequestMethodNotSupportedException(Exception ex) {
        ex.printStackTrace();
        return Result.error(false, ResultCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.message());
    }

    //SQL语法错误异常
    @ExceptionHandler(value = {SQLSyntaxErrorException.class})
    public Result sqlSyntaxErrorException(Exception ex) {
        ex.printStackTrace();
        return Result.error(false, ResultCode.SQL_SYNTAX_ERROR.message());
    }

    //方法参数类型不匹配异常
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result methodArgumentTypeMismatchException(Exception ex) {
        ex.printStackTrace();
        return Result.error(false, ResultCode.METHOD_ARGUMENT_TYPE_MISMATCH.message());
    }

    //错误的Sql语法异常
    @ExceptionHandler(value = {BadSqlGrammarException.class})
    public Result badSqlGrammarException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.BAD_SQL_GRAMMAR.code(), ResultCode.BAD_SQL_GRAMMAR.message());
    }

    //表单数据输入不完整
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public Result dataIntegrityViolationException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.DATA_INTEGRITY_VIOLATION.code(), ResultCode.DATA_INTEGRITY_VIOLATION.message());
    }

    //拦截被除数为0异常
    @ExceptionHandler(value = {ArithmeticException.class})
    public Result arithmeticException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.ARITHMETIC_EXCEPTION.code(), ResultCode.ARITHMETIC_EXCEPTION.message());
    }

    //拦截类型转换错误异常
    @ExceptionHandler(value = {ClassCastException.class})
    public Result classCastException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.CLASS_CAST.code(), ResultCode.CLASS_CAST.message());
    }

    //拦截空指针
    @ExceptionHandler(value = {NullPointerException.class})
    public Result nullPointerException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.NULL_POINTER.code(), ResultCode.NULL_POINTER.message());
    }

    //非法参数异常
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public Result illegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.ILLEGAL_PARAMETER.code(), ResultCode.ILLEGAL_PARAMETER.message());
    }

    //自定义异常处理器
    @ExceptionHandler(value = {MyException.class})
    public Result myException(MyException ex) {
        ex.printStackTrace();
        return Result.error(ex.getCode(), ex.getMessage());
    }

    //传递与接收的参数类型不匹配
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public Result httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.HTTP_MESSAGE_NOT_READABLE.code(), ResultCode.HTTP_MESSAGE_NOT_READABLE.message());
    }

    //拦截所有其他异常
    @ExceptionHandler
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.error(ResultCode.SERVER_FAILURE.code(), ResultCode.SERVER_FAILURE.message());
    }
}
