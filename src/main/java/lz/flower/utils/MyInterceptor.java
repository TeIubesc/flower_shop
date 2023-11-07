package lz.flower.utils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义拦截器，拦截除了有关登录注册等的其他页面
 *
 * @Author liuzhi
 * @Date 2022/10/12 22:16
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 在目标方法执行前执行
     *
     * @Author liuzhi
     * @Date 2022/10/12 22:17
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (token == null) {
            throw new MyException(ResultCode.USER_NOT_LOGIN.code(), ResultCode.USER_NOT_LOGIN.message());
        }
        //验证 token
        JwtUtil.checkSign(token);
        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getUserId(token);
//        System.out.println("id : " + userId);

        //获取 token 中的其他数据
        Map<String, Object> info = JwtUtil.getInfo(token);
//        info.forEach((k, v) -> System.out.println(k + ":" + v));
        return true;    //放行
    }
}
