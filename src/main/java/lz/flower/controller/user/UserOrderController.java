package lz.flower.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lz.flower.entity.Order;
import lz.flower.entity.OrderDetail;
import lz.flower.service.EvaluateService;
import lz.flower.service.OrderService;
import lz.flower.service.UserOrderService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户订单中心
 *
 * @Author liuzhi
 * @Date 2022/10/22 9:38
 */
@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    @Resource
    private UserOrderService userOrderService;
    @Resource
    private OrderService orderService;
    @Resource
    private EvaluateService evaluateService;

    /**
     * 获取当前用户的所有订单信息
     * @Author liuzhi
     * @Date 2022/10/22 9:39
     */
    @GetMapping("/{current}/{pageSize}/{userId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAllOrderByUserId(@PathVariable int current, @PathVariable int pageSize, @PathVariable String userId) {
        Order order = new Order();
        order.setUserId(userId);
        IPage<Order> page = userOrderService.getPage(current, pageSize, userId);
        for (Order record : page.getRecords()) {
            List<OrderDetail> orderDetail = orderService.getOrderDetail(record.getOrderId());
            for (OrderDetail detail : orderDetail) {
                //订单鲜花是否被评价
                detail.setEvaluated(evaluateService.isEvaluated(detail.getFlowerId(), detail.getUserId(), detail.getOrderNum()) > 0);
            }
            record.setDetailList(orderDetail);
            record.setOrderHistory(orderService.getOrderHistory(record.getOrderId()));
        }
        return new Result(true, page);
    }

    /**
     * 签收
     * @Author liuzhi
     * @Date 2023/4/3 15:14
     */
    @GetMapping("/orderReceipt/{orderId}")
    @Transactional
    public Result orderReceipt(@PathVariable String orderId) {
        if (userOrderService.orderReceipt(orderId) > 0) {
            return Result.success(ResultCode.SUCCESS.code(), "签收成功");
        } else {
            return Result.error(ResultCode.ERROR.code(), "签收失败");
        }
    }

    /**
     * 删除订单信息
     * @Author liuzhi
     * @Date 2023/4/5 11:19
     */
    @DeleteMapping("/{orderId}/{orderNum}")
    @Transactional
    public Result delUserOrderById(@PathVariable String orderId, @PathVariable String orderNum) {
        if (orderService.removeById(orderId)) {
            orderService.deleteOrderDetailByOrderNum(orderNum);
            orderService.deleteOrderHistoryByOrderId(orderId);
            return Result.success(ResultCode.SUCCESS.code(), "删除成功(●'◡'●)");
        } else
            return Result.error(ResultCode.ERROR.code(), "删除失败(＞﹏＜)");
    }
}
