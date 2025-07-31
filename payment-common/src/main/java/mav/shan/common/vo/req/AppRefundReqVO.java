package mav.shan.common.vo.req;

import lombok.Data;

@Data
public class AppRefundReqVO {
    private String billNo;
    private String refundNo;
    private Integer refundAmount;
    private Integer payOrderAmount;
}
