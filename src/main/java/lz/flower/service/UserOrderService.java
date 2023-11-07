package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.entity.OrderHistory;

import java.util.Date;

/**
 * 用户订单相关操作
 *
 * @Author liuzhi
 * @Date 2023/4/3 11:48
 */
public interface UserOrderService extends IService<Order> {

    /**
     * 分页获取
     *
     * @Author liuzhi
     * @Date 2022/10/22 22:15
     */
    IPage<Order> getPage(int current, int pageSize, String userId);   //方法重载

    /**
     * 用户订单签收
     *
     * @Author liuzhi
     * @Date 2022/10/22 22:18
     */
    Integer orderReceipt(String orderId);

    /**
     * 保存数据到订单详情表
     *
     * @Author liuzhi
     * @Date 2022/10/23 21:17
     */
    Integer saveOrderDetail(OrderDetail orderDetail);

    /**
     * 保存数据到订单历史信息表
     *
     * @Author liuzhi
     * @Date 2022/10/23 21:19
     */
    Integer saveOrderHistory(OrderHistory orderHistory);
}
