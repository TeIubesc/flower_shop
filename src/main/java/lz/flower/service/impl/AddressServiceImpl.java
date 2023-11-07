package lz.flower.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lz.flower.dao.AddressDao;
import lz.flower.entity.Address;
import lz.flower.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
}
