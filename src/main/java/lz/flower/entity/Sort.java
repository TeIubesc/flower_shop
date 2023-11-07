package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 鲜花分类表
 *
 * @Author liuzhi
 * @Date 2022/10/14 14:01
 */
@Data
@TableName("flower_sort")    //指定数据库表名
public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sortId")
    private Integer sortId;         //id
    private String purpose;         //用途
    private String material;        //花材
    private String color;           //颜色
    private String flowerId;       //鲜花id

    @TableField(exist = false)
    private String[] purposeArray;
    @TableField(exist = false)
    private String flowerName;
}
