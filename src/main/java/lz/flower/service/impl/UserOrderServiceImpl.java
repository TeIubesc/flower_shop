package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.OrderDao;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.entity.OrderHistory;
import lz.flower.service.UserOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserOrderServiceImpl extends ServiceImpl<OrderDao, Order> implements UserOrderService {


    @Override
    public IPage<Order> getPage(int current, int pageSize, String userId) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Order> lqw = new LambdaQueryWrapper<Order>();
        //condition为false时查询全部
        //订单号查询
        lqw.eq(userId != null && !userId.equals(""), Order::getUserId, userId);
        orderDao.selectPage(page, lqw);
        return page;
    }


    @Resource
    private OrderDao orderDao;

    @Override
    public Integer orderReceipt(String orderId) {
        return orderDao.orderReceipt(new Date(), orderId);
    }

    @Override
    public Integer saveOrderDetail(OrderDetail orderDetail) {
        return orderDao.saveOrderDetail(orderDetail);
    }

    @Override
    public Integer saveOrderHistory(OrderHistory orderHistory) {
        return orderDao.saveOrderHistory(orderHistory);
    }
}
