package lz.flower.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.FlowerStateDao;
import lz.flower.entity.FlowerState;
import lz.flower.service.FlowerStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 鲜花状态操作：是否设为热销商品、推荐商品、新品、特惠商品
 *
 * @Author liuzhi
 * @Date 2022/10/19 15:13
 */
@Service
public class FlowerStateServiceImpl extends ServiceImpl<FlowerStateDao, FlowerState> implements FlowerStateService {

    @Resource
    private FlowerStateDao flowerStateDao;

    /**
     * 鲜花状态获取
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:17
     */
    @Override
    public FlowerState getFlowerStateByFlowerId(String flowerId) {
        return flowerStateDao.getFlowerStateByFlowerId(flowerId);
    }

    /**
     * 鲜花状态修改
     *
     * @Author liuzhi
     * @Date 2022/10/19 15:56
     */
    @Override
    public boolean updateByFlowerId(FlowerState flowerState) {
        return flowerStateDao.updateByFlowerId(flowerState);
    }

    /**
     * 删除鲜花状态信息
     *
     * @Author liuzhi
     * @Date 2022/10/19 16:11
     */
    @Override
    public boolean delFlowerStateByFlowerId(String flowerId) {
        return flowerStateDao.delFlowerStateByFlowerId(flowerId);
    }
}
