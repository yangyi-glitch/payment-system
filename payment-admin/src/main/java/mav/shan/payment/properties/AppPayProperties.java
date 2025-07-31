package mav.shan.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "apppay")
public class AppPayProperties {
    // 微信域名
    private String domainName;
    // 微信支付
    private String wechatPayUrl;
    // 微信支付回调
    private String wechatPayCallbackUrl;
    // 微信退款
    private String wechatRefundUrl;
    // 微信退款查询
    private String wechatRefundQueryUrl;
    // 微信退款回调
    private String wechatRefundCallbackUrl;
    // appId
    private String appId;
    // APIv3密钥
    private String appSecret;
    // 证书序列号
    private String serialNumber;
    // 商户号
    private String mchId;
}
