package lz.flower.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告信息表
 *
 * @Author liuzhi
 * @Date 2022/10/19 22:24
 */

@Data
@TableName("notice_table")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "noticeId", type = IdType.ASSIGN_UUID)
    private String noticeId;       //公告id,ASSIGN_UUID:自动给主键生成UUID
    private String title;           //公告标题
    private String content;         //公告内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;       //公告发布时间
    private String picture;         //公告图片
}
