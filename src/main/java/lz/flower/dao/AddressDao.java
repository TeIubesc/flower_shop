package lz.flower.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lz.flower.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AddressDao extends BaseMapper<Address> {
}
