package lz.flower.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 鲜花销售统计(月份和每月销售金额)
 *
 * @Author liuzhi
 * @Date 2022/11/1 15:34
 */
@Data
public class SalesStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer month;
    private Float price;
}
