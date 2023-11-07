package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.LogInfo;

public interface LogInfoService extends IService<LogInfo> {

    /**
     * 获取所有日志信息
     * @Author liuzhi
     * @Date 2023/4/18 15:51
     */
    IPage<LogInfo> getPage(int current, int pageSize, LogInfo logInfo);
}
