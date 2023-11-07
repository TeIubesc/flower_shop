package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lz.flower.entity.Flower;
import lz.flower.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {

    /**
     * 查看当前用户的购物车中有哪些东西
     *
     * @Author liuzhi
     * @Date 2022/10/23 13:43
     */
    Page<Flower> getPage(IPage<Flower> iPage, String userId);

    /**
     * 判断当前用户购物车中是否已有该鲜花
     *
     * @Author liuzhi
     * @Date 2022/10/23 14:20
     */
    @Select("SELECT * from shoppingCart where flowerId = #{flowerId} and userId = #{userId}")
    ShoppingCart judgeExistence(String flowerId, String userId);

    /**
     * 移除购物车鲜花
     *
     * @Author liuzhi
     * @Date 2023/4/10 15:22
     */
    @Delete("delete from shoppingcart where cartId = #{cartId}")
    boolean removeById(String cartId);
}
