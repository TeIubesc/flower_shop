package lz.flower.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lz.flower.entity.Notice;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeService extends IService<Notice> {

    /**
     * 分页获取所有公告
     *
     * @Author liuzhi
     * @Date 2022/10/19 22:57
     */
    IPage<Notice> getPage(int current, int pageSize, Notice notice);

    /**
     * 添加公告信息
     *
     * @Author liuzhi
     * @Date 2022/10/20 14:44
     */
    boolean addNotice(MultipartFile file, Notice notice);

    /**
     * 修改公告信息
     *
     * @Author liuzhi
     * @Date 2022/10/20 14:47
     */
    boolean updateNotice(MultipartFile file, Notice notice);

    /**
     * 删除公告信息
     *
     * @Author liuzhi
     * @Date 2022/10/20 16:43
     */
    boolean removeById(String noticeId);


}
