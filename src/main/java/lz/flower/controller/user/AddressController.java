package lz.flower.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lz.flower.entity.Address;
import lz.flower.entity.User;
import lz.flower.service.AddressService;
import lz.flower.utils.Result;
import lz.flower.utils.ResultCode;
import org.apache.ibatis.annotations.Delete;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户地址相关操作
 *
 * @Author liuzhi
 * @Date 2022/10/21 22:08
 */

@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 获取当前用户的地址信息
     * @Author liuzhi
     * @Date 2022/10/21 22:32
     */
    @GetMapping("/getUserAddress/{userId}")
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result getAddressByUserId(@PathVariable String userId) {
        LambdaQueryWrapper<Address> lqw = new LambdaQueryWrapper<>();
        lqw.eq(userId != null, Address::getUserId, userId);
        List<Address> addressList = addressService.list(lqw);
        if (addressList != null)
            return Result.success(addressList);
        else
            return Result.error(ResultCode.ERROR.code(), "数据获取失败");
    }

    @PostMapping
    @Transactional
    public Result addAddress(@RequestBody Address address) {
        if (addressService.save(address))
            return Result.success(ResultCode.SUCCESS.code(), "添加成功( ￣^￣)");
        else
            return Result.error(ResultCode.ERROR.code(), "添加失败( >_<)");
    }

    @PutMapping
    @Transactional
    public Result updateAddress(@RequestBody Address address) {
        if (addressService.updateById(address))
            return Result.success(ResultCode.SUCCESS.code(), "修改成功( ￣^￣)");
        else
            return Result.error(ResultCode.ERROR.code(), "修改失败( >_<)");
    }

    @DeleteMapping("/{addressId}")
    @Transactional
    public Result delAddress(@PathVariable Integer addressId) {
        if (addressService.removeById(addressId))
            return Result.success(ResultCode.SUCCESS.code(), "删除成功( ￣^￣)");
        else
            return Result.error(ResultCode.DATA_NOT_EXIST.code(), ResultCode.DATA_NOT_EXIST.message());
    }

    @GetMapping("/{addressId}")
    @Transactional
    public Result getAddressById(@PathVariable Integer addressId) {
        Address address = addressService.getById(addressId);
        if (address != null)
            return Result.success(address);
        else
            return Result.error(ResultCode.DATA_NOT_EXIST.code(), ResultCode.DATA_NOT_EXIST.message());
    }
}
