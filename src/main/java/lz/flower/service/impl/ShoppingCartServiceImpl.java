package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.ShoppingCartDao;
import lz.flower.entity.*;
import lz.flower.service.FlowerService;
import lz.flower.service.ShoppingCartService;
import lz.flower.service.UserOrderService;
import lz.flower.utils.MyException;
import lz.flower.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Resource
    private ShoppingCartDao shoppingCartDao;
    @Resource
    private UserOrderService orderService;
    @Resource
    private FlowerService flowerService;

    @Override
    public Page<Flower> getPage(IPage<Flower> iPage, String userId) {
        return shoppingCartDao.getPage(iPage, userId);
    }

    @Override
    public boolean pay(List<Flower> flowerList, Address address, Float price) {
        Order order = new Order();
//        String orderNum = UUID.randomUUID().toString();
        String orderNum = String.valueOf(System.currentTimeMillis());
        order.setOrderNum(orderNum);
        order.setOrderTime(new Date());
        order.setState("未发货");
        order.setPrice(price);
        order.setUserId(address.getUserId());
        if (orderService.save(order)) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrderId(order.getOrderId());
            orderHistory.setUserName(address.getUserName());
            orderHistory.setTel(address.getTel());
            orderHistory.setAddress(address.getAddress());
            orderService.saveOrderHistory(orderHistory);
            for (Flower flower : flowerList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setDetailId(UUID.randomUUID().toString());
                orderDetail.setOrderNum(orderNum);
                orderDetail.setUnitPrice(flower.getDiscount() == 0 ? flower.getPrice() : flower.getDiscount());
                orderDetail.setQuantity(flower.getSell());
                orderDetail.setFlowerId(flower.getFlowerId());
                orderService.saveOrderDetail(orderDetail);
                shoppingCartDao.removeById(flower.getShoppingCartId());
                //更新鲜花售出数
                flowerService.updateFlowerSell(flower.getFlowerId(), flower.getSell());
                //更新鲜花库存数量
                flowerService.updateFlowerStock(flower.getFlowerId(), flower.getSell());
            }
        } else {
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MyException(ResultCode.ADD_ORDER_ERROR.code(), ResultCode.ADD_ORDER_ERROR.message());
        }
        return true;
    }

    @Override
    public boolean judgeExistence(String flowerId, String userId) {
        return shoppingCartDao.judgeExistence(flowerId, userId) != null;
    }

}
