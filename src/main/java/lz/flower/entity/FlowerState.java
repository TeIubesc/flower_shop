package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 鲜花状态：是否设为热销商品、推荐商品、新品、特惠商品
 *
 * @Author liuzhi
 * @Date 2022/10/19 15:09
 */

@TableName("flower_state")
@Data
public class FlowerState implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "stateId")
    private Integer stateId;    //状态id
    private Integer tuiJian;    //是否推荐
    private Integer xinPin;     //是否为新品
    private Integer reXiao;     //是否为热销
    private String flowerId;   //鲜花ID

    @TableField(exist = false)
    private List<Flower> flowerList;
}
