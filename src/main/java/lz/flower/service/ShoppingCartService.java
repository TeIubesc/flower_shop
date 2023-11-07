package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.*;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 查看当前用户的购物车中有哪些东西
     *
     * @Author liuzhi
     * @Date 2022/10/23 13:43
     */
    Page<Flower> getPage(IPage<Flower> iPage, String userId);

    /**
     * 购买
     *
     * @Author liuzhi
     * @Date 2023/4/10 13:42
     */
    boolean pay(List<Flower> flowerList, Address address, Float price);

    /**
     * 判断当前用户购物车中是否已有该鲜花
     *
     * @Author liuzhi
     * @Date 2022/10/23 14:23
     */
    boolean judgeExistence(String flowerId, String userId);
}
