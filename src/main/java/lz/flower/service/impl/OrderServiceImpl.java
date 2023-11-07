package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import lz.flower.dao.OrderDao;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.entity.OrderHistory;
import lz.flower.entity.SalesStatistics;
import lz.flower.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/5 17:14
 * 订单管理相关操作
 */

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Resource
    private OrderDao orderDao;

    /**
     * 分页查询
     *
     * @Author liuzhi
     * @Date 2022/10/5 17:49
     */
    @Override
    public IPage<Order> getPage(int current, int pageSize) {
        IPage page = new Page(current, pageSize);
        orderDao.selectPage(page, null);
        return page;
    }

    /**
     * @Author liuzhi
     * @Date 2022/10/5 17:48
     * 第一个参数：只有该参数是true时，才将like条件拼接到sql中；本例中，当houseHoldNum字段不为空时，则拼接name字段的like查询条件；
     * 第二个参数：该参数是数据库中的字段名；
     * 第三个参数：该参数值字段值；
     * 需要说明的是，这里的like查询是使用的默认方式，也就是说在查询条件的左右两边都有%：name= ‘%lz%'；
     * 如果只需要在左边或者右边拼接%，使用likeLeft或者likeRight方法。
     */
    @Override
    public IPage<Order> getPage(int current, int pageSize, Order order) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Order> lqw = new LambdaQueryWrapper<Order>();
        //condition为false时查询全部
        //订单号查询
        lqw.like(order.getOrderNum() != null, Order::getOrderNum, order.getOrderNum());
        lqw.eq(order.getUserId() != null, Order::getUserId, order.getUserId());
        orderDao.selectPage(page, lqw);
        return page;
    }

    @Override
    public boolean deliverGoods(Order order) {
        return orderDao.deliverGoods(order);
    }

    @Override
    public OrderHistory getOrderHistory(String orderId) {
        return orderDao.getOrderHistory(orderId);
    }


    @Override
    public boolean deleteOrderDetailByOrderNum(String orderNum) {
        return orderDao.deleteOrderDetailByOrderNum(orderNum);
    }

    @Override
    public boolean deleteOrderHistoryByOrderId(String orderId) {
        return orderDao.deleteOrderHistoryByOrderId(orderId);
    }

    @Override
    public List<SalesStatistics> flowerSalesStatistics(String year) {
        return orderDao.flowerSalesStatistics(year);
    }

    @Override
    public List<OrderDetail> getOrderDetail(String orderId) {
        return orderDao.getOrderDetail(orderId);
    }
}
