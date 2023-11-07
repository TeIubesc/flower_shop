package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/1 12:10
 * 鲜花信息表
 */
@Data
@TableName("flower_table")
public class Flower implements Serializable {   //一个类只有实现了Serializable接口，它的对象才能被序列化

    private static final long serialVersionUID = 1L;

    //映射数据库表中的主键，该字段与表中字段名不一样且不是驼峰式，则指定value
    @TableId(value = "flowerId", type = IdType.ASSIGN_UUID)
    private String flowerId;           //id
    private String flowerName;          //名称
    private String picture;         //图片(地址)
    private Integer stock;          //库存
    private String description;     //描述
    private Integer sell;           //售出数量
    private float price;            //单价
    private float discount;         //优惠价
    private float score;            //评分

    @TableField(exist = false)
    private String orderNum;

    @TableField(exist = false)
    private boolean rxFlag;
    @TableField(exist = false)
    private boolean xpFlag;
    @TableField(exist = false)
    private boolean tjFlag;

    @TableField(exist = false)
    private String shoppingCartId;
    @TableField(exist = false)
    private List<Evaluate> evaluateList;

}
