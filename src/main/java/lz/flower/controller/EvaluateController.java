package lz.flower.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lz.flower.entity.Evaluate;
import lz.flower.service.EvaluateService;
import lz.flower.service.FlowerService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author liuzhi
 * @Date 2022/10/8 10:18
 * 评价信息管理相关操作
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

    @Resource
    private EvaluateService evaluateService;
    @Resource
    private FlowerService flowerService;

    @GetMapping("/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAll(@PathVariable int current, @PathVariable int pageSize, Evaluate evaluate) {
        Page<Evaluate> allEvaluate = evaluateService.getAll(new Page<>(current, pageSize), evaluate);
        return new Result(true, allEvaluate);
    }

    /**
     * @Author liuzhi
     * @Date 2022/10/8 19:55
     * 删除评价信息
     */
    @DeleteMapping("/{evaluateId}")
    @Transactional
    public Result delEvaluateById(@PathVariable String evaluateId) {
        if (evaluateService.removeById(evaluateId))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功(●'◡'●)");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败,可能不存在该数据(＞﹏＜)");
    }

    /**
     * 批量删除
     *
     * @Author liuzhi
     * @Date 2023/3/27 10:45
     */
    @DeleteMapping("/delByIdList")
    @Transactional
    public Result delByIdList(@RequestBody JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("idArr");
        ArrayList<String> idList = new ArrayList<>();
        for (Object id : jsonArray) {
            idList.add((String) id);
        }
        if (evaluateService.removeByIds(idList))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功（＾∀＾●）ﾉｼ");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败(* ￣︿￣)");
    }

    /**
     * 用户订单页面添加评价信息
     *
     * @Author liuzhi
     * @Date 2023/4/11 16:37
     */
    @PostMapping
    @Transactional
    public Result addEvaluate(@RequestBody Evaluate evaluate) {
        evaluate.setEvaluateTime(new Date());
        if (evaluateService.save(evaluate)) {
            //更改鲜花评分
            Float flowerScoreAvg = evaluateService.getFlowerScoreAvg(evaluate.getFlowerId());
            flowerService.updateFlowerScore(evaluate.getFlowerId(), flowerScoreAvg);
            return Result.success(ResultCode.SUCCESS.code(), "评价成功");
        } else {
            return Result.error(ResultCode.ERROR.code(), "评价失败");
        }
    }

    /**
     * 查看用户该订单的该鲜花的评价信息
     * @Author liuzhi
     * @Date 2023/4/11 17:32
     */
    @PostMapping("/lookEvaluate")
    public Result lookEvaluate(@RequestBody Evaluate evaluate) {
        Evaluate evaluate1 = evaluateService.lookEvaluate(evaluate.getFlowerId(), evaluate.getUserId(), evaluate.getOrderNum());
        if (evaluate1 != null) {
            return Result.success(evaluate1);
        } else {
            return Result.error();
        }
    }

}
