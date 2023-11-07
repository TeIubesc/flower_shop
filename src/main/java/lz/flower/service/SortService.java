package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.Sort;

public interface SortService extends IService<Sort> {

    /**
     * 鲜花表、鲜花分类表，两表查询
     *
     * @Author liuzhi
     * @Date 2022/10/14 14:20
     */
    Page<Sort> getAll(IPage<Sort> iPage, Sort Sort);

    /**
     * 查询还没有分类的鲜花id
     *
     * @Author liuzhi
     * @Date 2022/10/14 17:06
     */
    String[] notSortFlowerId();

    /**
     * 可以分类的鲜花名称
     *
     * @Author liuzhi
     * @Date 2022/10/14 18:28
     */
    String[] canSortFlowerName(String[] integers);

    /**
     * 根据flowerID删除分类
     *
     * @Author liuzhi
     * @Date 2023/3/27 17:26
     */
    boolean delSortByFlowerId(String flowerId);
}
