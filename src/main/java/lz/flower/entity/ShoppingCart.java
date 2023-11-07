package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车表
 *
 * @Author liuzhi
 * @Date 2022/10/23 13:32
 */
@Data
@TableName("shoppingCart")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cartId", type = IdType.ASSIGN_UUID)
    private String cartId;    //购物车id
    private String flowerId;    //鲜花id
    private String userId;    //用户id
    private Integer quantity;    //加入的数量

    @TableField(exist = false)
    private List<Flower> flowerList;
}
