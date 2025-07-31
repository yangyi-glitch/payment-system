package mav.shan.common.vo.req;

import lombok.Data;

@Data
public class AppPayReqVO {

    /**
     * 支付金额 元
     */
    private String totalAmount;

    /**
     * 用户信息openId
     */
    private String openId;

    /**
     * 充值单号
     */
    private String businessNum;

    /**
     * 充值订单ID
     */
    private String orderId;

    /**
     * 充值人姓名
     */
    private String username;
}
