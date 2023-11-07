package lz.flower.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lz.flower.entity.Sort;
import lz.flower.service.FlowerService;
import lz.flower.service.SortService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/sort")
public class SortController {

    @Resource
    private SortService sortService;
    @Resource
    private FlowerService flowerService;

    @GetMapping("/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAll(@PathVariable int current, @PathVariable int pageSize, Sort sort) {
        Page<Sort> allSort = sortService.getAll(new Page<>(current, pageSize), sort);
        return new Result(true, allSort);
    }

    @PostMapping
    @Transactional
    public Result addSort(@RequestBody Sort sort) {
        //根据花名查id
        String flowerId = flowerService.selectByFlowerName(sort.getFlowerName());
        String purposeStr = ArrayUtils.toString(sort.getPurposeArray());
        purposeStr = purposeStr.substring(1, purposeStr.length() - 1);
        sort.setFlowerId(flowerId);
        sort.setPurpose(purposeStr);
        if (sortService.save(sort))
            return Result.success(ResultCode.SUCCESS.code(), "添加成功（￣︶￣）↗　");
        else
            return Result.error(ResultCode.ERROR.code(), "添加失败〒▽〒");
    }

    @PutMapping
    @Transactional
    public Result updateSort(@RequestBody Sort sort) {
        String purposeStr = ArrayUtils.toString(sort.getPurposeArray());
        purposeStr = purposeStr.substring(1, purposeStr.length() - 1);
        sort.setPurpose(purposeStr);
        if (sortService.updateById(sort))
            return Result.success(ResultCode.SUCCESS.code(), "修改成功(╹ڡ╹ )");
        else
            return Result.error(ResultCode.ERROR.code(), "修改失败＞︿＜");
    }

    @DeleteMapping("/{sortId}")
    @Transactional
    public Result delSortById(@PathVariable Integer sortId) {
        if (sortService.removeById(sortId))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功(❁´◡`❁)");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败≡(▔﹏▔)≡");
    }

    /**
     * 批量删除
     * @Author liuzhi
     * @Date 2023/3/28 17:00
     */
    @DeleteMapping("/delByIdList")
    @Transactional
    public Result delFlowerSortByIds(@RequestBody JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("idArr");
        ArrayList<Integer> idList = new ArrayList<>();
        for (Object id : jsonArray) {
            idList.add((Integer) id);
        }
        if (sortService.removeByIds(idList))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功(❁´◡`❁)");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败≡(▔﹏▔)≡");
    }

    /**
     * 可被分类的鲜花名
     * @Author liuzhi
     * @Date 2023/3/28 10:47
     */
    @GetMapping("/canSortFlowerName")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result canSortFlowerName() {
        String[] flowerIds = sortService.notSortFlowerId();
        //可分类的鲜花名称，如果查询出来的id数组为空则会获取全部的鲜花名称，所以这里限制一下
        if (flowerIds.length != 0) {
            String[] flowerNames = sortService.canSortFlowerName(flowerIds);
            return Result.success(flowerNames);
        } else
            return Result.error("", "无数据");
    }
}
