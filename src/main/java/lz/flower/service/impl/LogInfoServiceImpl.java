package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.LogInfoDao;
import lz.flower.entity.LogInfo;
import lz.flower.service.LogInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoDao, LogInfo> implements LogInfoService {

    @Resource
    private LogInfoDao logInfoDao;

    @Override
    public IPage<LogInfo> getPage(int current, int pageSize, LogInfo logInfo) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<LogInfo> lqw = new LambdaQueryWrapper<>();
        lqw.like(logInfo.getUserName()!= null && !logInfo.getUserName().equals(""),LogInfo::getUserName,logInfo.getUserName());
        lqw.like(logInfo.getQueryTime()!= null && !logInfo.getQueryTime().equals(""),LogInfo::getCreateTime,logInfo.getQueryTime());
        logInfoDao.selectPage(page,lqw);
        return page;
    }
}
