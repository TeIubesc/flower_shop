package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户地址表
 *
 * @Author liuzhi
 * @Date 2022/10/21 22:08
 */

@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "addressId")
    private Integer addressId;
    private String userName;
    private String address;
    private String tel;
    private String userId;
}
