package lz.flower.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lz.flower.entity.FlowerState;
import lz.flower.service.FlowerService;
import lz.flower.service.FlowerStateService;
import lz.flower.service.SortService;
import lz.flower.utils.MyException;
import lz.flower.utils.Result;
import lz.flower.entity.Flower;
import lz.flower.utils.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flower")
public class FlowerController {

    @Resource
    private FlowerService flowerService;
    @Resource
    private FlowerStateService flowerStateService;
    @Resource
    private SortService sortService;
    @Value("${page.flowerImg}")
    private String imgPath;    //鲜花图片存放路径


    /**
     * 添加鲜花数据
     * @Author liuzhi
     * @Date 2022/10/16 14:27
     */
    @PostMapping
    @Transactional
    public Result addFlower(MultipartFile file, Flower flower) {
        if (!(flowerService.selectByFlowerName(flower.getFlowerName()) == null)) {
            return Result.error(ResultCode.FLOWER_ALREADY_EXISTS.code(), ResultCode.FLOWER_ALREADY_EXISTS.message());
        }
        if (flowerService.addFlower(file, flower)) {
            return Result.success(ResultCode.SUCCESS.code(), "添加成功（＾▽＾）");
        } else {
            return Result.error(ResultCode.ERROR.code(), "添加失败(。>︿<)_θ");
        }
    }

    @GetMapping("/{current}/{pageSize}")
    @Transactional
    public Result getAllFlower(@PathVariable Integer current, @PathVariable Integer pageSize, Flower flower) {
        IPage<Flower> page = flowerService.getPage(current, pageSize, flower);
        if (current > page.getPages()) //当前页大于总页数
            page = flowerService.getPage((int) page.getPages(), pageSize, flower);
        return new Result(true, page);
    }

    /**
     * 获取flower状态
     *
     * @Author liuzhi
     * @Date 2023/3/27 16:30
     */
    @GetMapping("/flowerStatus/{flowerId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getFlowerStatusById(@PathVariable String flowerId) {
        //获取鲜花状态信息
        FlowerState flowerState = flowerStateService.getFlowerStateByFlowerId(flowerId);
        if (flowerState != null) {
            Flower flower = new Flower();
            flower.setRxFlag(flowerState.getReXiao() == 1 ? true : false);
            flower.setTjFlag(flowerState.getTuiJian() == 1 ? true : false);
            flower.setXpFlag(flowerState.getXinPin() == 1 ? true : false);
            return Result.success(flower);
        } else {
            return Result.error(ResultCode.ERROR.code(), "获取鲜花信息失败");
        }
    }

    @PutMapping
    @Transactional
    public Result updateFlower(MultipartFile file, Flower flower) {
        if (flowerService.editFlower(file, flower))
            return Result.success(ResultCode.SUCCESS.code(), "修改成功ʕ•ᴥ•ʔ");
        else
            return Result.error(ResultCode.ERROR.code(), "修改失败(๑＞ ＜)☆");
    }

    @DeleteMapping("/{flowerId}")
    @Transactional
    public Result delFlowerById(@PathVariable String flowerId, @RequestParam(required = false) String picture) {
        if (picture != null && !picture.equals("")) {
            if (flowerService.removeById(flowerId)) {
                File file = new File(imgPath + picture);
                if (file.exists())
                    file.delete();
                sortService.delSortByFlowerId(flowerId);
                flowerStateService.delFlowerStateByFlowerId(flowerId);
                return Result.success(ResultCode.SUCCESS.code(), "删除成功(。・・)ノ");
            }
        }
        return Result.error(ResultCode.ERROR.code(), "删除失败ಠಿ_ಠ");
    }

    /**
     * 批量删除
     * @Author liuzhi
     * @Date 2023/3/28 9:16
     */
    @DeleteMapping("/delByIdList")
    @Transactional
    public Result delFlowerByIdList(@RequestBody JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("idArr");
        ArrayList<String> idList = new ArrayList<>();
        for (Object id : jsonArray) {
            idList.add((String) id);
        }
        if (flowerService.removeByIds(idList))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功（＾∀＾●）ﾉｼ");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败(* ￣︿￣)");
    }


}
