package lz.flower.controller;

import lz.flower.entity.User;
import lz.flower.service.UserService;
import lz.flower.utils.JwtUtil;
import lz.flower.utils.MD5;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class LoginController {
    @Resource
    private UserService userService;

    /**
     * 登录验证
     * @Author liuzhi
     * @Date 2022/10/9 16:12
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam(required = false) String power, HttpServletRequest request) {
        User currentUser = userService.userAuthentication(user);
        if (currentUser == null)
            return Result.error(ResultCode.USER_LOGIN_ERROR.code(), ResultCode.USER_LOGIN_ERROR.message()); //账号或密码错误
        //对数据库二次加密的密码解密
        String jmPassword = MD5.md5MinusSalt(currentUser.getPassword());
        //前端密码加密
        String password = DigestUtils.md5Hex(user.getPassword());
        if(!jmPassword.equals(password)){
            //密码错误
            return Result.error(ResultCode.USER_LOGIN_ERROR.code(), ResultCode.USER_LOGIN_ERROR.message()); //账号或密码错误
        }
        //是否是管理员登录
        if (!power.equals("user"))
            if (!currentUser.getPower().equals("管理员")) {
                return Result.error(ResultCode.NO_LOGIN_PERMISSION.code(), ResultCode.NO_LOGIN_PERMISSION.message());  //没有登陆权限
            }
        Map<String, Object> info = new HashMap<>();
        info.put("username", currentUser.getUserName());
        info.put("password", currentUser.getPassword());
        //生成JWT字符串
        String token = JwtUtil.sign(currentUser.getUserId().toString(), info);
        request.setAttribute("username",currentUser.getUserName());

        currentUser.setPassword(user.getPassword());
        if (currentUser != null){
            return Result.success(currentUser, token);  //登录成功，并返回token
        }
        else
            return Result.error(ResultCode.ERROR.code(), "未知错误,登录失败(≧﹏ ≦)");
    }

    /**
     * 注册
     * @Author liuzhi
     * @Date 2022/10/12 19:24
     */
    @PostMapping("/userRegister")
    @Transactional
    public Result userRegister(@RequestBody User user) {
        if (!userService.checkCode(user.getEmail(), user.getCode()))
            return Result.error(ResultCode.CODE_ERROR.code(), ResultCode.CODE_ERROR.message());

        //密码MD5加密
        user.setPassword(MD5.md5PlusSalt(user.getPassword()));
        //默认用户名
        user.setUserName("用户" + UUID.randomUUID().toString().substring(0,4));

        if (userService.save(user)) {
            return Result.success(ResultCode.SUCCESS.code(), "注册成功(✿◠‿◠)");
        } else {
            return Result.error(ResultCode.USER_REGISTER_ERROR.code(), ResultCode.USER_REGISTER_ERROR.message());
        }
    }

    /**
     * 判断账号是否重复
     * @Author liuzhi
     * @Date 2022/10/12 17:42
     */
    @GetMapping("/getByAccount")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getByAccount(@RequestParam(required = false) String account) {
        if (userService.getByAccount(account) == null) {
            return Result.success(ResultCode.SUCCESS.code(), "该账号可用");
        } else {
            return Result.error(ResultCode.USER_HAS_EXISTED.code(), ResultCode.USER_HAS_EXISTED.message());
        }
    }

    /**
     * 判断账号与邮箱是否匹配
     * @Author liuzhi
     * @Date 2022/11/3 12:36
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @GetMapping("/getEmailByAccount/{account}/{email}")
    public Result getEmailByAccount(@PathVariable String account, @PathVariable String email) {
        User user = userService.getByAccount(account);
        if (user == null)
            return Result.error(ResultCode.USER_NOT_EXIST.code(), ResultCode.USER_NOT_EXIST.message());
        if (!email.equals(user.getEmail())) {
            return Result.error(ResultCode.ACCOUNT_DONT_MATCH_EMAIL.code(), ResultCode.ACCOUNT_DONT_MATCH_EMAIL.message());
        }
        return Result.success("匹配");
    }

    /**
     * 发送邮件验证码
     * @Author liuzhi
     * @Date 2022/10/12 18:31
     */
    @Transactional
    @GetMapping("/sendMail/{email}")
    public Result sendMail(@PathVariable String email) {
        userService.sendMail(email);
        return Result.success(ResultCode.SUCCESS.code(), "验证码已发送至邮箱(。・∀・)ノ");
    }

    /**
     * 退出登录
     * @Author liuzhi
     * @Date 2022/10/12 23:07
     */
    @GetMapping("/logout")
    @Transactional
    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }

    /**
     * 忘记密码
     * @Author liuzhi
     * @Date 2022/11/3 12:53
     */
    @PostMapping("/modifyPassword")
    @Transactional
    public Result modifyPassword(@RequestBody User user) {
        if (!userService.checkCode(user.getEmail(), user.getCode()))
            return Result.error(ResultCode.CODE_ERROR.code(), ResultCode.CODE_ERROR.message());
        if (userService.modifyPassword(user) > 0)
            return Result.success(ResultCode.SUCCESS.code(), "密码修改成功(✿◠‿◠)");
        else
            return Result.error(ResultCode.ERROR.code(), "密码修改失败(≧﹏ ≦)");
    }
}
