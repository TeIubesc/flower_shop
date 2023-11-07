package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.Flower;
import lz.flower.entity.Sort;
import org.springframework.web.multipart.MultipartFile;

public interface FlowerService extends IService<Flower> {

    /**
     * 分页获取
     *
     * @Author liuzhi
     * @Date 2022/10/3 20:23
     */
    IPage<Flower> getPage(int current, int pageSize, String type, String flowerName);

    IPage<Flower> getPage(int current, int pageSize, Flower flower);   //方法重载

    /**
     * 根据花名查询鲜花id
     *
     * @Author liuzhi
     * @Date 2022/10/14 20:36
     */
    String selectByFlowerName(String flowerName);

    /**
     * 添加鲜花
     *
     * @Author liuzhi
     * @Date 2022/10/27 11:23
     */
    Boolean addFlower(MultipartFile file, Flower flower);

    /**
     * 修改鲜花
     *
     * @Author liuzhi
     * @Date 2022/10/27 11:25
     */
    Boolean editFlower(MultipartFile file, Flower flower);

    /**
     * 前台主页
     *
     * @Author liuzhi
     */
    //新品鲜花
    IPage<Flower> getNewProduct(int current, int pageSize, String flowerName);

    //特惠鲜花
    IPage<Flower> getDiscount(int current, int pageSize, String flowerName);

    //热销鲜花
    IPage<Flower> getHotSale(int current, int pageSize, String flowerName);

    //推荐鲜花
    IPage<Flower> getRecommend(int current, int pageSize, String flowerName);

    //所有鲜花/分类
    IPage<Flower> flowerSort(int current, int pageSize, Sort sort);

    /**
     * 鲜花详情页数据
     *
     * @Author liuzhi
     * @Date 2023/4/11 11:50
     */
    Flower flowerDetail(String flowerId);

    /**
     * 更改鲜花售出的数量
     *
     * @Author liuzhi
     * @Date 2022/10/26 13:13
     */
    boolean updateFlowerSell(String flowerId, Integer quantity);

    /**
     * 更新鲜花评分
     *
     * @Author liuzhi
     * @Date 2022/10/28 12:55
     */
    boolean updateFlowerScore(String flowerId, Float score);

    /**
     * 更新库存数量
     *
     * @Author liuzhi
     * @Date 2022/10/28 13:40
     */
    boolean updateFlowerStock(String flowerId, Integer quantity);
}
