package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 公告信息管理
 *
 * @Author liuzhi
 * @Date 2022/10/19 22:23
 */
@Repository
@Mapper
public interface NoticeDao extends BaseMapper<Notice> {
}
