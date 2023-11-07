package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lz.flower.entity.Evaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/7 20:29
 * 评价信息管理相关操作
 */
@Repository
@Mapper
public interface EvaluateDao extends BaseMapper<Evaluate> {
    /**
     * @Author liuzhi
     * @Date 2022/10/7 20:29
     * 多表连接查询
     */
    Page<Evaluate> getAll(IPage<Evaluate> iPage, Evaluate evaluate);

    /**
     * 判断用户是否已经评价了该订单的该鲜花
     *
     * @Author liuzhi
     * @Date 2022/10/26 15:54
     */
    @Select("SELECT count(*) FROM evaluate where flowerId = #{flowerId} and userId = #{userId} and orderNum = #{orderNum}")
    Integer isEvaluated(String flowerId, String userId, String orderNum);

    /**
     * 查看用户该订单的该鲜花的评价信息
     *
     * @Author liuzhi
     * @Date 2022/10/27 8:41
     */
    @Select("SELECT * FROM evaluate where flowerId = #{flowerId} and userId = #{userId} and orderNum = #{orderNum}")
    Evaluate lookEvaluate(String flowerId, String userId, String orderNum);

    /**
     * 根据鲜花ID获取鲜花的平均分
     *
     * @Author liuzhi
     * @Date 2022/10/28 12:52
     */
    @Select("select avg(score) from evaluate where flowerId = #{flowerId}")
    Float getFlowerScoreAvg(String flowerId);
}