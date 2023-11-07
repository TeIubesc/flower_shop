package lz.flower.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lz.flower.entity.LogInfo;
import lz.flower.service.LogInfoService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/logInfo")
public class LogInfoController {

    @Resource
    private LogInfoService logInfoService;

    /**
     * 获取所有日志信息
     * @Author liuzhi
     * @Date 2023/4/18 15:51
     */
    @GetMapping("/{current}/{pageSize}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAll(@PathVariable Integer current, @PathVariable Integer pageSize,
                         @RequestParam(required = false,defaultValue = "") String createTime,
                         @RequestParam(required = false,defaultValue = "") String userName
                         ) {
        LogInfo logInfo = new LogInfo();
        logInfo.setQueryTime(createTime);
        logInfo.setUserName(userName);
        IPage<LogInfo> page = logInfoService.getPage(current, pageSize, logInfo);
        return Result.success(page);
    }

    /**
     * 删除日志信息
     * @Author liuzhi
     * @Date 2023/4/18 16:50
     */
    @DeleteMapping("/{logId}")
    @Transactional
    public Result delLogById(@PathVariable String logId){
        if(logInfoService.removeById(logId)){
            return Result.success(ResultCode.SUCCESS.code(),"删除成功");
        }else{
            return Result.error();
        }
    }
}
