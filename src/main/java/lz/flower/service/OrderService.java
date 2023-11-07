package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.entity.OrderHistory;
import lz.flower.entity.SalesStatistics;

import java.util.Date;
import java.util.List;

public interface OrderService extends IService<Order> {
    /**
     * 分页查询
     *
     * @Author liuzhi
     * @Date 2022/10/5 17:32
     */
    IPage<Order> getPage(int current, int pageSize);

    IPage<Order> getPage(int current, int pageSize, Order order);   //方法重载

    /**
     * 获取订单历史信息
     *
     * @Author liuzhi
     * @Date 2022/10/15 15:58
     */
    OrderHistory getOrderHistory(String orderId);

    /**
     * 获取订单详情
     *
     * @Author liuzhi
     * @Date 2023/4/3 11:46
     */
    List<OrderDetail> getOrderDetail(String orderId);

    /**
     * 发货
     *
     * @Author liuzhi
     * @Date 2022/10/7 14:38
     */
    boolean deliverGoods(Order order);

    /**
     * 删除订单及关联表信息
     *
     * @Author liuzhi
     * @Date 2022/10/22 12:29
     */
    boolean deleteOrderDetailByOrderNum(String orderNum);

    boolean deleteOrderHistoryByOrderId(String orderId);

    /**
     * 鲜花销售统计(月份和每月销售金额)
     *
     * @Author liuzhi
     * @Date 2022/11/1 15:35
     */
    List<SalesStatistics> flowerSalesStatistics(String year);

}
