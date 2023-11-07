package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.Flower;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/1 12:14
 * 鲜花管理相关操作
 */

@Repository
@Mapper
public interface FlowerDao extends BaseMapper<Flower> {

    /**
     * @Author liuzhi
     * @Date 2022/10/6 16:23
     * 一对一查询，根据订单表的鲜花id查询出鲜花数据，一个订单对应一个鲜花
     */
    @Select("select * from flower_table where flowerId=#{flowerId}")
    List<Flower> selectByFlowerId(Integer flowerId);

    /**
     * 根据花名查询鲜花id,查询出可以分类的鲜花 判断鲜花是否重复（2022/10/16 14：24）
     *
     * @Author liuzhi
     * @Date 2022/10/14 20:35
     */
    @Select("select flowerId from flower_table where flowerName = #{flowerName}")
    String selectByFlowerName(String flowerName);


    /**
     * 按用途分类获取鲜花id
     *
     * @Author liuzhi
     * @Date 2022/10/25 14:54
     */
    List<Integer> getSortIds(String sortType, String sortName);

    /**
     * 更改鲜花售出的数量
     *
     * @Author liuzhi
     * @Date 2022/10/26 13:11
     */
    @Update("update flower_table set sell = sell + #{quantity} where flowerId = #{flowerId}")
    boolean updateFlowerSell(String flowerId, Integer quantity);

    /**
     * 更新鲜花的评分
     *
     * @Author liuzhi
     * @Date 2022/10/28 12:49
     */
    @Update("update flower_table set score = #{score} where flowerId = #{flowerId}")
    boolean updateFlowerScore(String flowerId, Float score);

    /**
     * 更新库存数量
     *
     * @Author liuzhi
     * @Date 2022/10/28 13:39
     */
    @Update("update flower_table set stock = stock - #{quantity} where flowerId = #{flowerId}")
    boolean updateFlowerStock(String flowerId, Integer quantity);

}
