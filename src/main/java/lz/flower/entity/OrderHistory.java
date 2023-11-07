package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单历史信息表，存放死数据
 *
 * @Author liuzhi
 * @Date 2022/10/15 15:52
 */

@Data
@TableName("order_history")
public class OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ohId;
    private String orderId;
    private String userName;
    private String tel;
    private String address;
    private String beiZhu;
}
