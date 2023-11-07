package lz.flower.utils;

import com.auth0.jwt.JWT;
import lz.flower.entity.LogInfo;
import lz.flower.service.LogInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Component
@Aspect
public class LogAspect {
    //引入日志配置
    private static final Log LOG = LogFactory.getLog(LogAspect.class);

    @Resource
    private LogInfoService logInfoService;

    /**
     * 定义一个切入点 只拦截controller.
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 定义在web包或者子包
     * ~ 第三个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(* lz.flower.controller..*.*(..))")
    public void logPointcut() {
    }

    //!execution(* lz.flower.controller.LogInfoController.*.*(..))
    @Pointcut("execution(* lz.flower.controller.LogInfoController.*(..))")
    public void excludePointcutWeb(){};
    @Pointcut("execution(* lz.flower.controller.user.FlowerPageController.*(..))")
    public void excludePointcutWeb2(){};

    //around(和上面的方法名一样)
    @Around("logPointcut() && !excludePointcutWeb() && !excludePointcutWeb2()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        long start = System.currentTimeMillis();
        //请求地址
        String url = request.getRequestURI();
        //用户ip
        String ip = request.getRemoteAddr();
        //映射方法
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //参数
        String args = Arrays.toString(joinPoint.getArgs());
        LogInfo logInfo = new LogInfo();
        //token中的用户信息
        String token = request.getHeader("token");
        String username = "";
        logInfo.setCreateTime(new Date());
        logInfo.setUrl(url);
        logInfo.setIp(ip);
        logInfo.setMethod(method);
        logInfo.setArgs(args);
        try {
            Object result = joinPoint.proceed();
            if(token != null){
                Map<String, Object> info = JwtUtil.getInfo(token);
                username = info.get("username").toString();
            }else{
                username = (String) request.getAttribute("username");
            }
            logInfo.setUserName(username);
            long end = System.currentTimeMillis();
            logInfo.setDuration((end - start) + " ms");
            //操作成功
            logInfo.setState("1");
            logInfoService.save(logInfo);
            return result;
        } catch (Throwable e) {
            if(token != null){
                Map<String, Object> info = JwtUtil.getInfo(token);
                username = info.get("username").toString();
            }else{
                username = (String) request.getAttribute("username");
            }
            logInfo.setUserName(username);
            long end = System.currentTimeMillis();
            logInfo.setDuration((end - start) + " ms");
            //操作失败
            logInfo.setState("2");
            logInfoService.save(logInfo);
            throw e;
        }
    }
}