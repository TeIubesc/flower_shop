package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/7 20:19
 * 鲜花评价表
 */
@Data
public class Evaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "evaluateId", type = IdType.ASSIGN_UUID)
    private String evaluateId;           //id
    private String info;                  //评价信息

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date evaluateTime;            //评价时间
    private Integer score;              //评分
    private String flowerId;             //鲜花id
    private String userId;               //用户id
    private String orderNum;                  //订单号

    /**
     * @Author liuzhi
     * @Date 2022/10/8 11:29
     * 接收查询条件
     */
    @TableField(exist = false)
    private String flowerName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String userTx;
    @TableField(exist = false)
    private String formatDate;
}
