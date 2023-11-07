package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.Flower;
import lz.flower.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/3 20:22
 * 用户管理相关操作
 */

@Repository
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user_table where userId=#{userId}")
    List<User> selectByUserId(Integer userId);

    /**
     * 修改用户头像
     *
     * @Author liuzhi
     * @Date 2022/10/10 19:09
     */
    @Update("update user_table set txPicture = #{fileName} where userId = #{userId}")
    boolean changeTx(String userId, String fileName);

    /**
     * 登录验证
     *
     * @Author liuzhi
     * @Date 2022/10/11 20:40
     */
    @Select("SELECT * FROM user_table where account = #{account}")
    User userAuthentication(User user);

    /**
     * 判断账号是否重复
     *
     * @Author liuzhi
     * @Date 2022/10/12 17:43
     */
    @Select("SELECT * FROM user_table where account = #{account}")
    User getByAccount(String account);

    /**
     * 用户注册
     *
     * @Author liuzhi
     * @Date 2022/10/12 19:43
     */
    @Insert("insert into user_table(userName,account,password,email,introduce) values(#{userName},#{account},#{password},#{email},#{introduce})")
    Integer userRegister(User user);

    /**
     * (忘记密码)修改密码
     *
     * @Author liuzhi
     * @Date 2022/11/3 12:55
     */
    @Update("update user_table set password = #{password} where account = #{account}")
    Integer modifyPassword(User user);
}
