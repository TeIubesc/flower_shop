package lz.flower.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lz.flower.entity.*;
import lz.flower.service.FlowerService;
import lz.flower.service.OrderService;
import lz.flower.service.ShoppingCartService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.apache.ibatis.annotations.Insert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 购物车相关操作
 *
 * @Author liuzhi
 * @Date 2022/10/23 13:35
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;


    /**
     * 获取所有数据
     *
     * @Author liuzhi
     * @Date 2023/4/5 13:48
     */
    @GetMapping("/{current}/{pageSize}/{userId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getFlowersByUserId(@PathVariable Integer current, @PathVariable Integer pageSize, @PathVariable String userId) {
        IPage<Flower> page = shoppingCartService.getPage(new Page<>(current, pageSize), userId);
        return new Result(true, page);
    }

    /**
     * 更改购物车鲜花数量
     * @Author liuzhi
     * @Date 2023/4/5 13:55
     */
    @PostMapping("/changeFlowerNum")
    @Transactional
    public Result changeFlowerNum(@RequestBody ShoppingCart shoppingCart) {
        if (shoppingCartService.updateById(shoppingCart))
            return Result.success();
        else
            return Result.error();
    }

    /**
     * 移除购物车鲜花
     * @Author liuzhi
     * @Date 2023/4/5 14:18
     */
    @DeleteMapping("/delShoppingCartFlower/{shoppingCarId}")
    @Transactional
    public Result delShoppingCartFlower(@PathVariable String shoppingCarId) {
        if (shoppingCartService.removeById(shoppingCarId))
            return Result.success(ResultCode.SUCCESS.code(), "移除成功");
        else
            return Result.error();
    }

    /**
     * 购买
     * @Author liuzhi
     * @Date 2023/4/10 11:24
     */
    @PostMapping("/pay")
    @Transactional
    public Result pay(@RequestBody JSONObject jsonObject) {
        //JSONObject转实体类
        JSONObject addressJo = jsonObject.getJSONObject("address");
        Address address = JSONObject.toJavaObject(addressJo, Address.class);
        //JSONObject转List
        JSONArray flowerJa = jsonObject.getJSONArray("flowerList");
        List<Flower> flowerList = JSONObject.parseArray(flowerJa.toJSONString(), Flower.class);
        String price = jsonObject.get("price").toString();
        if (shoppingCartService.pay(flowerList, address, Float.valueOf(price))) {
            return Result.success("购买成功");
        } else
            return Result.error();
    }

    /**
     * 添加到购物车
     * @Author liuzhi
     * @Date 2023/4/11 14:50
     */
    @PostMapping("/addToShoppingCart")
    @Transactional
    public Result addToShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        if (shoppingCartService.judgeExistence(shoppingCart.getFlowerId(), shoppingCart.getUserId())) {
            return Result.error(ResultCode.FLOWER_IS_IN_SHOPPINGCART.code(), ResultCode.FLOWER_IS_IN_SHOPPINGCART.message());
        }
        if (shoppingCartService.save(shoppingCart)) {
            return Result.success(ResultCode.SUCCESS.code(), "已成功加入购物车✪ ω ✪");
        } else {
            return Result.error(ResultCode.ERROR.code(), "加入购物车失败╥﹏╥");
        }
    }
}
