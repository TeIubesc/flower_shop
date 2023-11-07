package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.LogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogInfoDao extends BaseMapper<LogInfo> {
}
