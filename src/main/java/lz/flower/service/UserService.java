package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    /**
     * 分页获取
     *
     * @Author liuzhi
     * @Date 2022/10/3 21:25
     */
    IPage<User> getPage(int current, int pageSize);

    IPage<User> getPage(int current, int pageSize, User user);   //方法重载

    /**
     * 修改头像
     *
     * @Author liuzhi
     * @Date 2022/10/10 19:12
     */
    boolean changeTx(MultipartFile file, @PathVariable String userId);

    /**
     * 登录验证
     *
     * @Author liuzhi
     * @Date 2022/10/11 20:47
     */
    User userAuthentication(User user);

    /**
     * 判断账号是否重复
     *
     * @Author liuzhi
     * @Date 2022/10/12 17:45
     */
    User getByAccount(String account);

    /**
     * 发送邮件验证码
     *
     * @Author liuzhi
     * @Date 2022/10/12 18:41
     */
    void sendMail(String email);

    /**
     * 邮件验证码验证
     *
     * @Author liuzhi
     * @Date 2022/10/12 18:45
     */
    boolean checkCode(String mail, String code);

    /**
     * (忘记密码)修改密码
     *
     * @Author liuzhi
     * @Date 2022/11/3 12:57
     */
    Integer modifyPassword(User user);
}
