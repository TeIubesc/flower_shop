package lz.flower.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lz.flower.entity.Flower;
import lz.flower.entity.Notice;
import lz.flower.entity.Sort;
import lz.flower.service.FlowerService;
import lz.flower.service.NoticeService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 各种(特惠、热销...)鲜花页面的数据获取
 *
 * @Author liuzhi
 * @Date 2022/10/21 12:31
 */
@RestController
@RequestMapping("/person/flower")
public class FlowerPageController {
    @Resource
    private FlowerService flowerService;
    @Resource
    private NoticeService noticeService;

    /**
     * 新品鲜花
     *
     * @Author liuzhi
     * @Date 2023/3/31 10:43
     */
    @GetMapping("/getNewProduct/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getNewProduct(@PathVariable Integer current, @PathVariable Integer pageSize,
                                @RequestParam(required = false) String flowerName
    ) {
        IPage<Flower> page = flowerService.getNewProduct(current, pageSize, flowerName);
        if (page.getRecords() == null) {
            return Result.error(ResultCode.ERROR.code(), "暂无数据");
        } else {
            return Result.success(page);
        }
    }

    /**
     * 限时特惠
     *
     * @Author liuzhi
     * @Date 2023/3/31 10:44
     */
    @GetMapping("/getDiscount/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getDiscount(@PathVariable Integer current, @PathVariable Integer pageSize,
                              @RequestParam(required = false) String flowerName) {
        IPage<Flower> page = flowerService.getDiscount(current, pageSize, flowerName);
        if (page.getRecords() == null) {
            return Result.error(ResultCode.ERROR.code(), "暂无数据");
        } else {
            return Result.success(page);
        }
    }

    /**
     * 热销
     *
     * @Author liuzhi
     * @Date 2023/3/31 11:34
     */
    @GetMapping("/getHotSale/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getHotSale(@PathVariable Integer current, @PathVariable Integer pageSize,
                             @RequestParam(required = false) String flowerName) {
        IPage<Flower> page = flowerService.getHotSale(current, pageSize, flowerName);
        if (page.getRecords() == null) {
            return Result.error(ResultCode.ERROR.code(), "暂无数据");
        } else {
            return Result.success(page);
        }
    }

    /**
     * 推荐
     *
     * @Author liuzhi
     * @Date 2023/3/31 11:34
     */
    @GetMapping("/getRecommend/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getRecommend(@PathVariable Integer current, @PathVariable Integer pageSize,
                               @RequestParam(required = false) String flowerName) {
        IPage<Flower> page = flowerService.getRecommend(current, pageSize, flowerName);
        if (page.getRecords() == null) {
            return Result.error(ResultCode.ERROR.code(), "暂无数据");
        } else {
            return Result.success(page);
        }
    }

    /**
     * 鲜花分类
     *
     * @Author liuzhi
     * @Date 2023/3/31 15:47
     */
    @PostMapping("/flowerSort/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getFlowerSort(@PathVariable Integer current, @PathVariable Integer pageSize, @RequestBody Sort sort) {
        IPage<Flower> page = flowerService.flowerSort(current, pageSize, sort);
        if (page.getRecords() == null) {
            return Result.error(ResultCode.ERROR.code(), "暂无数据");
        } else {
            return Result.success(page);
        }
    }

    /**
     * 鲜花详情
     * @Author liuzhi
     * @Date 2023/4/11 11:48
     */
    @GetMapping("/flowerDetail/{flowerId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result flowerDetail(@PathVariable String flowerId) {
        Flower flower = flowerService.flowerDetail(flowerId);
        if (flower != null)
            return Result.success(flower);
        else
            return Result.error(ResultCode.ERROR.code(), "数据获取失败");
    }

    /**
     * 前台主页获取公告信息
     * @Author liuzhi
     * @Date 2023/4/12 10:23
     */
    @GetMapping("/getNotice")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getNotice() {
        List<Notice> noticeList = noticeService.list();
        return new Result(true, noticeList);
    }
}
