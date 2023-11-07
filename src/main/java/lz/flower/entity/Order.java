package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/5 17:16
 * 订单信息表，鲜花ID用于多表查询
 * 订单表不能用:order，因为与关键字冲突
 */
@Data
@TableName("order_table")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    //映射数据库表中的主键，该字段与表中字段名不一样且不是驼峰式，则指定value
    @TableId(value = "orderId", type = IdType.ASSIGN_UUID)
    private String orderId;    //id
    private String orderNum;      //订单号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //格式化后传到前端
    @DateTimeFormat(pattern = "yyyy-MM-dd")     //从前端接收
    private Date orderTime;         //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliverTime;       //发货时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiptTime;       //收货时间
    private String state;       //订单状态
    private float price;       //订单价格
    private String userId;       //购买用户id

    @TableField(exist = false)
    private List<OrderDetail> detailList;

    @TableField(exist = false)
    private OrderHistory orderHistory;  //订单历史

    @TableField(exist = false)
    private String formatDate;

}
