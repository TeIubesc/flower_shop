package lz.flower.utils;

import org.springframework.stereotype.Component;

/**
 * 生成6位数的邮件验证码
 *
 * @Author liuzhi
 * @Date 2022/10/12 18:41
 */
@Component
public class CodeUtils {
    public String generateCode(String email) { //生成6位随机加密验证码
        int hash = email.hashCode();
        long result = hash ^ 20221012;  //加密
        result = result ^ System.currentTimeMillis();
        String code = String.valueOf(result);
        return code.substring(code.length() - 6);
    }
}
