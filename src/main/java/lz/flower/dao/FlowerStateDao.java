package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.FlowerState;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FlowerStateDao extends BaseMapper<FlowerState> {

    /**
     * 鲜花状态获取
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:16
     */
    @Select("select tuiJian,xinPin,reXiao from flower_state where flowerId = #{flowerId}")
    FlowerState getFlowerStateByFlowerId(String flowerId);

    /**
     * 鲜花状态修改
     *
     * @Author liuzhi
     * @Date 2022/10/19 15:55
     */
    @Update("update flower_state set tuiJian = #{tuiJian},reXiao = #{reXiao},xinPin = #{xinPin} where flowerId = #{flowerId}")
    boolean updateByFlowerId(FlowerState flowerState);

    /**
     * 鲜花状态删除
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:09
     */
    @Delete("delete from flower_state where flowerId = #{flowerId}")
    boolean delFlowerStateByFlowerId(String flowerId);
}
