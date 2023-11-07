package lz.flower.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lz.flower.entity.Notice;
import lz.flower.service.NoticeService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 获取所有公告
     *
     * @Author liuzhi
     * @Date 2022/10/19 22:57
     */
    @GetMapping("/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAll(@PathVariable Integer current, @PathVariable Integer pageSize, Notice notice) {
        IPage<Notice> page = noticeService.getPage(current, pageSize, notice);
        if (current > page.getPages()) //当前页大于总页数
            page = noticeService.getPage((int) page.getPages(), pageSize, notice);
        return new Result(true, page);
    }

    /**
     * 根据id获取公告信息
     * @Author liuzhi
     * @Date 2022/10/19 22:57
     */
    @GetMapping("/{noticeId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getNoticeById(@PathVariable String noticeId) {
        Notice notice = noticeService.getById(noticeId);
        if(notice != null){
            return Result.success(notice);
        }else{
            return Result.error();
        }
    }

    /**
     * 添加公告信息
     * @Author liuzhi
     * @Date 2022/10/20 14:44
     */
    @PostMapping
    @Transactional
    public Result addNotice(MultipartFile file, Notice notice) {//防止文件名冲突
        if (noticeService.addNotice(file, notice)) {
            return Result.success(ResultCode.SUCCESS.code(), "添加成功（＾∀＾●）ﾉｼ");
        } else {
            return Result.error(ResultCode.ERROR.code(), "添加失败(* ￣︿￣)");
        }
    }

    /**
     * 修改公告信息
     * @Author liuzhi
     * @Date 2022/10/20 14:47
     */
    @PutMapping
    @Transactional
    public Result updateNotice(MultipartFile file, Notice notice) {
        if (noticeService.updateNotice(file, notice))
            return Result.success(ResultCode.SUCCESS.code(), "修改成功（＾∀＾●）ﾉｼ");
        else
            return Result.success(ResultCode.ERROR.code(), "修改失败(* ￣︿￣)");
    }

    /**
     * 删除公告信息
     * @Author liuzhi
     * @Date 2022/10/20 16:43
     */
    @DeleteMapping("/{noticeId}")
    @Transactional
    public Result delNoticeById(@PathVariable String noticeId) {
        if (noticeService.removeById(noticeId))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功（＾∀＾●）ﾉｼ");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败(* ￣︿￣)");
    }

    /**
     * 批量删除
     * @Author liuzhi
     * @Date 2023/3/25 19:50
     */
    @DeleteMapping("/delByIdList")
    @Transactional
    public Result delNoticeByIdList(@RequestBody JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("idArr");
        ArrayList<String> idList = new ArrayList<>();
        for (Object id : jsonArray) {
            idList.add((String) id);
        }
        if (noticeService.removeByIds(idList))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功（＾∀＾●）ﾉｼ");
        else
            return Result.error(ResultCode.ERROR.code(), "删除失败(* ￣︿￣)");
    }
}
