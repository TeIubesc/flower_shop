package lz.flower.controller.user;

import lz.flower.entity.Evaluate;
import lz.flower.entity.Flower;
import lz.flower.entity.User;
import lz.flower.service.EvaluateService;
import lz.flower.service.FlowerService;
import lz.flower.utils.Result;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户评价相关操作
 *
 * @Author liuzhi
 * @Date 2022/10/26 14:49
 */
@RestController
@RequestMapping("/user/evaluate")
public class UserEvaluateController {

    @Resource
    private EvaluateService evaluateService;
    @Resource
    private FlowerService flowerService;

    /**
     * 用户评论
     * @Author liuzhi
     * @Date 2022/10/26 15:49
     */
    @PostMapping
    @Transactional
    public Result userWriteEvaluate(@RequestBody Flower flower, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return new Result(false, "身份信息过期,请重新登录ヽ(゜▽゜　)－");
        Evaluate evaluate = new Evaluate();
        evaluate.setFlowerId(flower.getFlowerId());
        evaluate.setUserId(user.getUserId());
        evaluate.setEvaluateTime(new Date());
        evaluate.setInfo(flower.getDescription());
        evaluate.setScore((int) flower.getScore());
        evaluate.setOrderNum(flower.getOrderNum());

        boolean b = evaluateService.save(evaluate);
        boolean c = false;
        if (b) {
            Float flowerScoreAvg = evaluateService.getFlowerScoreAvg(flower.getFlowerId());
            c = flowerService.updateFlowerScore(flower.getFlowerId(), flowerScoreAvg);
        }
        return new Result(b && c, b && c ? "评价成功(づ￣ 3￣)づ" : "评价失败ヽ(゜▽゜　)－");
    }

    /**
     * 查看评价信息
     *
     * @Author liuzhi
     * @Date 2022/10/27 8:31
     */
    @GetMapping("/lookEvaluate/{flowerId}/{orderNum}")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Result lookEvaluate(@PathVariable String flowerId, @PathVariable String orderNum, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //Integer count = evaluateService.userEvaluated(flowerId, user.getUserId(), orderNum);
        Evaluate evaluate = evaluateService.lookEvaluate(flowerId, user.getUserId(), orderNum);
        return new Result(evaluate != null, evaluate);
    }

    /**
     * 评论修改
     *
     * @Author liuzhi
     * @Date 2022/10/27 9:29
     */
    @PutMapping
    @Transactional
    public Result updateEvaluateById(@RequestBody Evaluate evaluate) {
        boolean b = evaluateService.updateById(evaluate);
        return new Result(b, b ? "修改成功¬_¬" : "修改失败O.O");
    }

    /**
     * 评论删除
     *
     * @Author liuzhi
     * @Date 2022/10/27 9:33
     */
    @DeleteMapping("/{evaluateId}")
    @Transactional
    public Result deleteEvaluateById(@PathVariable Integer evaluateId) {
        boolean b = evaluateService.removeById(evaluateId);
        return new Result(b, b ? "删除成功¬_¬" : "删除失败O.O");
    }
}
