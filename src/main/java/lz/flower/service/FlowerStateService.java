package lz.flower.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.FlowerState;

public interface FlowerStateService extends IService<FlowerState> {

    /**
     * 鲜花状态获取
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:17
     */
    FlowerState getFlowerStateByFlowerId(String flowerId);

    /**
     * 鲜花状态修改
     *
     * @Author liuzhi
     * @Date 2022/10/19 15:56
     */
    boolean updateByFlowerId(FlowerState flowerState);

    /**
     * 删除鲜花状态信息
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:11
     */
    boolean delFlowerStateByFlowerId(String flowerId);
}
