package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.entity.OrderHistory;
import lz.flower.entity.SalesStatistics;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/5 17:17
 * 订单管理相关操作
 */

@Repository
@Mapper
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 发货
     *
     * @Author liuzhi
     * @Date 2022/10/7 14:38
     */
    boolean deliverGoods(Order order);

    /**
     * 获取订单详情及历史信息
     *
     * @Author liuzhi
     * @Date 2022/10/15 15:57
     */
    @Select("select * from order_history where orderId = #{orderId}")
    OrderHistory getOrderHistory(String orderId);

    List<OrderDetail> getOrderDetail(String orderId);

    /**
     * 删除订单详情表中的信息
     *
     * @Author liuzhi
     * @Date 2022/10/22 12:29
     */
    @Delete("delete from order_detail where orderNum = #{orderNum}")
    boolean deleteOrderDetailByOrderNum(String orderNum);

    /**
     * 删除订单历史信息表中的信息
     *
     * @Author liuzhi
     * @Date 2022/10/22 12:29
     */
    @Delete("delete from order_history where orderId = #{orderId}")
    boolean deleteOrderHistoryByOrderId(String orderId);

    /**
     * 用户订单签收
     *
     * @Author liuzhi
     * @Date 2022/10/22 22:14
     */
    @Update("update order_table set receiptTime = #{receiptTime},state = '已签收' where orderId = #{orderId}")
    Integer orderReceipt(Date receiptTime, String orderId);

    /**
     * 保存数据到订单详情表
     *
     * @Author liuzhi
     * @Date 2022/10/23 21:14
     */
    @Insert("insert into order_detail(detailId,orderNum,flowerId,unitPrice,quantity) values(#{detailId},#{orderNum},#{flowerId},#{unitPrice},#{quantity})")
    Integer saveOrderDetail(OrderDetail orderDetail);

    /**
     * 保存数据到订单历史信息表
     *
     * @Author liuzhi
     * @Date 2022/10/23 21:18
     */
    @Insert("insert into order_history(orderId,userName,tel,address,beiZhu) values(#{orderId},#{userName},#{tel},#{address},#{beiZhu})")
    Integer saveOrderHistory(OrderHistory orderHistory);

    /**
     * 鲜花销售统计(月份和每月销售金额)
     *
     * @Author liuzhi
     * @Date 2022/11/1 15:24
     */
    @Select("select MONTH(orderTime) as month,sum(price) as price from order_table where YEAR(orderTime) = #{year} GROUP BY MONTH(orderTime)")
    List<SalesStatistics> flowerSalesStatistics(String year);

}
