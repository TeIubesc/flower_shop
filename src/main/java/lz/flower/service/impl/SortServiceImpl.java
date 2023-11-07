package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.SortDao;
import lz.flower.entity.Sort;
import lz.flower.service.SortService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SortServiceImpl extends ServiceImpl<SortDao, Sort> implements SortService {

    @Resource
    private SortDao sortDao;

    /**
     * 鲜花表、鲜花分类表，两表查询
     *
     * @Author liuzhi
     * @Date 2022/10/14 14:20
     */
    @Override
    public Page<Sort> getAll(IPage<Sort> iPage, Sort Sort) {
        return sortDao.getAll(iPage, Sort);
    }


    /**
     * 查询还没有分类的鲜花id
     *
     * @Author liuzhi
     * @Date 2022/10/14 17:06
     */
    @Override
    public String[] notSortFlowerId() {
        return sortDao.notSortFlowerId();
    }

    /**
     * 可以分类的鲜花名称
     *
     * @Author liuzhi
     * @Date 2022/10/14 18:28
     */
    @Override
    public String[] canSortFlowerName(String[] idArr) {
        return sortDao.canSortFlowerName(idArr);
    }

    /**
     * 根据flowerID删除分类
     *
     * @Author liuzhi
     * @Date 2023/3/27 17:26
     */
    @Override
    public boolean delSortByFlowerId(String flowerId) {
        return sortDao.delSortByFlowerId(flowerId);
    }

}
