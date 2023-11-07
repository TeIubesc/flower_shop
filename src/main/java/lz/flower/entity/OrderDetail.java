package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 订单详情表
 *
 * @Author liuzhi
 * @Date 2022/10/15 10:24
 */

@Data
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String detailId;       //订单详情id
    private String orderNum;        //订单号
    private String flowerId;       //鲜花id
    private float unitPrice;        //鲜花单价
    private Integer quantity;       //购买数量

    @TableField(exist = false)
    private Flower flower;

    @TableField(exist = false)
    private boolean evaluated;
    @TableField(exist = false)
    private String userId;
    @TableField(exist = false)
    private String flowerName;
    @TableField(exist = false)
    private String picture;

}
