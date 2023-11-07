package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.FlowerDao;
import lz.flower.entity.*;
import lz.flower.service.EvaluateService;
import lz.flower.service.FlowerService;
import lz.flower.service.FlowerStateService;
import lz.flower.service.UserService;
import lz.flower.utils.MyException;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author liuzhi
 * @Date 2022/10/1 12:15
 * 鲜花管理相关操作
 * 通过类的继承或实现来提供方法，不需要手动写CURD方法
 * 当接口提供的方法不满足实际需求时,也可以在里面自定义方法,然后回归传统写法
 */
@Service
public class FlowerServiceImpl extends ServiceImpl<FlowerDao, Flower> implements FlowerService {

    @Autowired
    private FlowerDao flowerDao;
    @Resource
    private FlowerStateService flowerStateService;
    @Resource
    private EvaluateService evaluateService;
    @Resource
    private UserService userService;
    @Value("${page.flowerImg}")
    private String imgPath;    //鲜花图片存放路径

    @Override
    public IPage<Flower> getPage(int current, int pageSize, String type, String flowerName) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Flower> lqw = new LambdaQueryWrapper<Flower>();
        lqw.inSql((type != null) && (type != ""), Flower::getFlowerId, "select flowerId from flower_state where " + type + " = 1");
        lqw.like((flowerName != null) && (flowerName != ""), Flower::getFlowerName, flowerName);
        flowerDao.selectPage(page, lqw);
        return page;
    }

    /**
     * @Author liuzhi
     * @Date 2022/10/3 20:23
     * 第一个参数：只有该参数是true时，才将like条件拼接到sql中；本例中，当houseHoldNum字段不为空时，则拼接name字段的like查询条件；
     * 第二个参数：该参数是数据库中的字段名；
     * 第三个参数：该参数值字段值；
     * 需要说明的是，这里的like查询是使用的默认方式，也就是说在查询条件的左右两边都有%：name= ‘%lz%'；
     * 如果只需要在左边或者右边拼接%，使用likeLeft或者likeRight方法。
     */
    @Override
    public IPage<Flower> getPage(int current, int pageSize, Flower flower) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Flower> lqw = new LambdaQueryWrapper<Flower>();
        //condition为false时查询全部
        lqw.like(flower.getFlowerName() != null, Flower::getFlowerName, flower.getFlowerName());
        lqw.like(flower.getDescription() != null, Flower::getDescription, flower.getDescription());
        flowerDao.selectPage(page, lqw);
        return page;
    }

    /**
     * 根据花名查询鲜花id
     *
     * @Author liuzhi
     * @Date 2022/10/14 20:36
     */
    @Override
    public String selectByFlowerName(String flowerName) {
        return flowerDao.selectByFlowerName(flowerName);
    }

    @Override
    public Boolean addFlower(MultipartFile file, Flower flower) {
        try {
            String fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            flower.setPicture(fileName);
            file.transferTo(new File(imgPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(ResultCode.FILE_UPLOAD_FAIL.code(), ResultCode.FILE_UPLOAD_FAIL.message());
        }
        String flowerId = flowerDao.insert(flower) > 0 ? flower.getFlowerId() : "";
        if (flowerId.equals(""))
            return false;
        else {
            FlowerState flowerState = new FlowerState();
            flowerState.setReXiao(flower.isRxFlag() ? 1 : 0);
            flowerState.setTuiJian(flower.isTjFlag() ? 1 : 0);
            flowerState.setXinPin(flower.isXpFlag() ? 1 : 0);
            flowerState.setFlowerId(flower.getFlowerId());
            flowerState.setFlowerId(flowerId);
            if (flowerStateService.save(flowerState)) {
                return true;
            } else {
                throw new MyException(ResultCode.ERROR.code(), "鲜花状态添加异常");
            }
        }
    }

    @Override
    public Boolean editFlower(MultipartFile file, Flower flower) {
        String fileName = "";
        if (file != null) {
            try {
                fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                File imgFile = new File(imgPath + flower.getPicture());
                if (imgFile.exists()) {
                    imgFile.delete();
                }
                file.transferTo(new File(imgPath + fileName));
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ResultCode.FILE_UPLOAD_FAIL.code(), ResultCode.FILE_UPLOAD_FAIL.message());
            }
            flower.setPicture(fileName);
        }
        if (flowerDao.updateById(flower) > 0) {
            //更改鲜花状态信息
            FlowerState flowerState = new FlowerState();
            flowerState.setReXiao(flower.isRxFlag() ? 1 : 0);
            flowerState.setTuiJian(flower.isTjFlag() ? 1 : 0);
            flowerState.setXinPin(flower.isXpFlag() ? 1 : 0);
            flowerState.setFlowerId(flower.getFlowerId());
            if (flowerStateService.updateByFlowerId(flowerState))
                return true;
            else
                return false;
        }
        return true;
    }

    @Override
    public IPage<Flower> getNewProduct(int current, int pageSize, String flowerName) {
        return getPage(current, pageSize, "xinPin", flowerName);
    }

    @Override
    public IPage<Flower> getDiscount(int current, int pageSize, String flowerName) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Flower> lqw = new LambdaQueryWrapper<Flower>();
        lqw.like(flowerName != null, Flower::getFlowerName, flowerName);
        lqw.gt(true, Flower::getDiscount, 0);
        flowerDao.selectPage(page, lqw);
        return page;
    }

    @Override
    public IPage<Flower> getHotSale(int current, int pageSize, String flowerName) {
        return getPage(current, pageSize, "reXiao", flowerName);
    }

    @Override
    public IPage<Flower> getRecommend(int current, int pageSize, String flowerName) {
        return getPage(current, pageSize, "tuiJian", flowerName);
    }

    @Override
    public IPage<Flower> flowerSort(int current, int pageSize, Sort sort) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Flower> lqw = new LambdaQueryWrapper<>();
        lqw.inSql(sort.getFlowerName() != null && sort.getFlowerName() != "", Flower::getFlowerId, "select flowerId from flower_table where flowerName like '%" + sort.getFlowerName() + "%'");
        lqw.inSql(sort.getPurpose() != null && sort.getPurpose() != "", Flower::getFlowerId, "select flowerId from flower_sort where purpose like '%" + sort.getPurpose() + "%'");
        lqw.inSql(sort.getMaterial() != null && sort.getMaterial() != "", Flower::getFlowerId, "select flowerId from flower_sort where material like '%" + sort.getMaterial() + "%'");
        lqw.inSql(sort.getColor() != null && sort.getColor() != "", Flower::getFlowerId, "select flowerId from flower_sort where color like '%" + sort.getColor() + "%'");
        flowerDao.selectPage(page, lqw);
        return page;
    }

    @Override
    public Flower flowerDetail(String flowerId) {
        Flower flower = flowerDao.selectById(flowerId);
        LambdaQueryWrapper<Evaluate> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Evaluate::getFlowerId, flowerId);
        if (evaluateService.count(lqw) != 0) {
            lqw.inSql(Evaluate::getFlowerId, "select flowerId from evaluate where flowerId = " + flowerId);
            List<Evaluate> evaluateList = evaluateService.list(lqw);
            for (Evaluate evaluate : evaluateList) {
                User user = userService.getById(evaluate.getUserId());
                evaluate.setUserName(user.getUserName());
                evaluate.setUserTx(user.getTxPicture());
            }
            flower.setEvaluateList(evaluateList);
        }
        return flower;
    }

    @Override
    public boolean updateFlowerSell(String flowerId, Integer quantity) {
        return flowerDao.updateFlowerSell(flowerId, quantity);
    }

    @Override
    public boolean updateFlowerScore(String flowerId, Float score) {
        return flowerDao.updateFlowerScore(flowerId, score);
    }

    @Override
    public boolean updateFlowerStock(String flowerId, Integer quantity) {
        return flowerDao.updateFlowerStock(flowerId, quantity);
    }
}
