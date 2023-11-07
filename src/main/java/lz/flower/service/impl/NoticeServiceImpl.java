package lz.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.NoticeDao;
import lz.flower.entity.Notice;
import lz.flower.service.NoticeService;
import lz.flower.utils.MyException;
import lz.flower.utils.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

    @Resource
    private NoticeDao noticeDao;
    @Value("${page.noticeImg}")
    private String noticeImg;    //公告图片存放路径

    @Override
    public IPage<Notice> getPage(int current, int pageSize, Notice notice) {
        IPage page = new Page(current, pageSize);
        LambdaQueryWrapper<Notice> lqw = new LambdaQueryWrapper<>();
        lqw.like(notice.getTitle() != null, Notice::getTitle, notice.getTitle());
        noticeDao.selectPage(page, lqw);
        return page;
    }

    @Override
    public boolean addNotice(MultipartFile file, Notice notice) {
        String fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if (uploadImg(file, notice, fileName)) {
            notice.setReleaseTime(new Date());
            notice.setPicture(fileName);
        }
        return noticeDao.insert(notice) > 0;
    }

    @Override
    public boolean removeById(String noticeId) {
        Notice notice = noticeDao.selectById(noticeId);
        try {
            File file = new File(noticeImg + notice.getPicture());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new MyException(ResultCode.DATA_NOT_EXIST.code(), ResultCode.DATA_NOT_EXIST.message());
        }
        return noticeDao.deleteById(noticeId) > 0;
    }

    @Override
    public boolean updateNotice(MultipartFile file, Notice notice) {
        String fileName = "";
        if (file != null) {
            fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (uploadImg(file, notice, fileName)) {
                notice.setPicture(fileName);
            } else
                return false;
        }
        notice.setReleaseTime(new Date());
        return noticeDao.updateById(notice) > 0;
    }

    /**
     * 图片上传
     *
     * @Author liuzhi
     * @Date 2022/10/20 14:33
     */
    public boolean uploadImg(MultipartFile file, Notice notice, String fileName) {
        // 上传图片
        if (file != null)
            try {
                file.transferTo(new File(noticeImg + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException(ResultCode.FILE_UPLOAD_FAIL.code(), ResultCode.FILE_UPLOAD_FAIL.message());
            }

        if (notice.getNoticeId() != null && file != null) {   //修改图片后删除原来的图片
            Notice noticeById = noticeDao.selectById(notice.getNoticeId());
            File imgFile = new File(noticeImg + noticeById.getPicture());
            if (imgFile.exists())
                imgFile.delete();
        }
        return true;
    }
}
