package lz.flower.utils;

/**
 * 返回异常信息和代码
 *
 * @Author liuzhi
 * @Date 2023/3/25 22:28
 */

public enum ResultCode {
    /* 默认成功状态码 */
    SUCCESS(200, "操作成功"),
    /* 默认失败状态码 */
    ERROR(210, "操作失败，未知错误信息"),

    /* 用户错误：20001-29999*/
    NO_LOGIN_PERMISSION(20001, "没有登陆权限~~>_<~~"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误~~>_<~~"),
    USER_NOT_EXIST(20004, "账户不存在"),
    USER_HAS_EXISTED(20005, "该账号已存在,换一个(¬‿¬)"),
    CODE_ERROR(20006, "验证码错误（￣^￣）"),
    USER_REGISTER_ERROR(20007, "未知错误,注册失败~~>_<~~"),
    ACCOUNT_DONT_MATCH_EMAIL(20008, "账号与绑定邮箱不匹配"),

    /**
     * 自定义拦截器
     *
     * @Author liuzhi
     */
    SERVER_FAILURE(30000, "服务器故障，请稍后再试"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(30001, "不支持该http请求方式"),
    SQL_SYNTAX_ERROR(30002, "SQL语法错误(＞﹏＜)"),
    METHOD_ARGUMENT_TYPE_MISMATCH(30003, "方法参数类型不匹配,暂无该数据(＞﹏＜)"),
    BAD_SQL_GRAMMAR(30004, "错误的Sql语法,请检查表名或字段名是否与数据库关键字冲突(＞﹏＜)"),
    DATA_INTEGRITY_VIOLATION(30005, "请输入完整的数据(＞﹏＜)"),
    ARITHMETIC_EXCEPTION(30006, "服务器故障,被除数为0(＞﹏＜)"),
    CLASS_CAST(30007, "类型转换异常，请稍后再试(＞﹏＜)"),
    NULL_POINTER(30008, "空指针异常，请稍后再试(＞﹏＜)"),
    TOKEN_LOSE_EFFICACY(30010, "token失效,请重新登录"),
    USER_NOT_LOGIN(30010, "token验证失败,请先登录"),
    ILLEGAL_PARAMETER(30011, "非法参数异常"),
    HTTP_MESSAGE_NOT_READABLE(30012, "传递与接收的参数类型不匹配"),
    FILE_UPLOAD_FAIL(30013, "图片上传失败"),

    /**
     * 操作失败
     *
     * @Author liuzhi
     */
    DATA_NOT_EXIST(40001, "操作失败,该数据不存在"),
    FLOWER_ALREADY_EXISTS(40002, "已存在该鲜花名称，换一个(。>︿<)_θ"),
    ADD_ORDER_ERROR(40003, "添加订单异常,请稍后再试"),
    DATE_ERROR(40004, "操作失败,日期数据有误"),
    FLOWER_IS_IN_SHOPPINGCART(40005, "购物车中已存在该鲜花啦￣^￣");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
