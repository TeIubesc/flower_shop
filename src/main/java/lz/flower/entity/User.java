package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author liuzhi
 * @Date 2022/10/3 20:20
 * 用户信息表
 * 用户表不能用:user，因为与关键字冲突
 */

@Data
@TableName("user_table")    //指定数据库表名
public class User implements Serializable {     //一个类只有实现了Serializable接口，它的对象才能被序列化

    private static final long serialVersionUID = 1L;

    //映射数据库表中的主键，该字段与表中字段名不一样且不是驼峰式，则指定value
    @TableId(value = "userId", type = IdType.ASSIGN_UUID)
    private String userId;           //id
    private String userName;          //名称
    private String account;           //账号
    private String password;         //密码
    private String power;          //权限（管理员 or 用户）
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date regTime;           //注册时间
    private String email;           //邮箱
    private String sex;             //性别
    private String introduce;       //个人简介
    private String txPicture;       //头像图片名

    @TableField(exist = false)
    private String code;        //验证码
}
