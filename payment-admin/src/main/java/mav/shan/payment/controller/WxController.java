package mav.shan.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import com.wechat.pay.java.service.refund.RefundService;
import lombok.extern.slf4j.Slf4j;
import mav.shan.common.utils.PayUtil;
import mav.shan.common.vo.req.AppPayReqVO;
import mav.shan.common.vo.req.AppRefundReqVO;
import mav.shan.payment.properties.AppPayProperties;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static mav.shan.common.utils.FileUtils.streamTostring;

@Slf4j
@Service
public class WxController {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .build();

    @Value("${spring.profiles.active}")
    private String active;

    static final int TAG_LENGTH_BIT = 128;
    @Resource
    private AppPayProperties appPayProperties;

    private JsapiService jsapiservice;
    private RefundService refundService;

    private PrivateKey privateKey;
    private static final String algorithm = "SHA256withRSA";

    @PostConstruct
    public void initWechatPayConfig() {
        privateKey = getPrivateKey();
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(appPayProperties.getMchId())
                        .privateKey(privateKey)
                        .merchantSerialNumber(appPayProperties.getSerialNumber())
                        .apiV3Key(appPayProperties.getAppSecret())
                        .build();
        jsapiservice = new JsapiService.Builder().config(config).build();
    }

    public Map<String, Object> pay(AppPayReqVO appPayReqVO) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        String ytotalAmount = PayUtil.changeY2F(appPayReqVO.getTotalAmount());
        amount.setTotal(Integer.valueOf(ytotalAmount));
        request.setAmount(amount);
        request.setAppid(appPayProperties.getAppId());
        request.setMchid(appPayProperties.getMchId());
        request.setDescription("测试商品标题");
        request.setNotifyUrl(appPayProperties.getWechatPayCallbackUrl());
        request.setOutTradeNo(appPayReqVO.getBusinessNum());
        Payer payer = new Payer();
        payer.setOpenid(appPayReqVO.getOpenId());
        request.setPayer(payer);
        long timestamp = System.currentTimeMillis();
        PrepayResponse response = jsapiservice.prepay(request);
        return getPaymentData(String.valueOf(timestamp), response.getPrepayId());
    }

    public String refund(AppRefundReqVO appRefundReqVO) {
        Map<String, Object> params = new HashMap();
        params.put("out_trade_no", appRefundReqVO.getBillNo());
        params.put("out_refund_no", appRefundReqVO.getRefundNo());
        params.put("notify_url", appPayProperties.getWechatRefundCallbackUrl());

        Map<String, Object> amountMap = new HashMap<>();
        // 退款金额
        amountMap.put("refund", appRefundReqVO.getRefundAmount());
        // 订单金额
        amountMap.put("total", appRefundReqVO.getPayOrderAmount());
        // 退款币种
        amountMap.put("currency", "CNY");
        params.put("amount", amountMap);

        String authorization = buildAuthorization(appPayProperties.getMchId(),
                appPayProperties.getSerialNumber(),
                "POST",
                appPayProperties.getWechatRefundUrl(),
                JSON.toJSONString(params));

        Request request = new Request.Builder()
                .addHeader("Authorization", authorization)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .url(appPayProperties.getDomainName() + appPayProperties.getWechatRefundUrl())
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(params)))
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string;
        } catch (Exception e) {
            log.warn("微信退款失败===>{}", e);
            return "";
        }
    }

    public String refundQuery(String billNo) {
        String url = appPayProperties.getWechatRefundQueryUrl() + billNo;
        String authorization = buildAuthorization(appPayProperties.getMchId(),
                appPayProperties.getSerialNumber(),
                "GET",
                url,
                null);
        Request request = new Request.Builder()
                .addHeader("Authorization", authorization)
                .addHeader("Accept", "application/json")
                .url(appPayProperties.getDomainName() + url)
                .get()
                .build();
        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string;
        } catch (Exception e) {
            log.warn("微信退款失败===>{}", e);
            return "";
        }
    }

    /**
     * 微信回调解密
     *
     * @param associatedDataStr
     * @param nonceStr
     * @param ciphertext
     * @return
     */
    public String decryptToString(String associatedDataStr, String nonceStr, String ciphertext) {
        byte[] associatedData = associatedDataStr.getBytes(StandardCharsets.UTF_8);
        byte[] nonce = nonceStr.getBytes(StandardCharsets.UTF_8);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(appPayProperties.getAppSecret().getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData);
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), "utf-8");
        } catch (Exception e) {
            log.warn("支付回调解密失败===>{}", e);
            return "";
        }
    }

    /**
     * 组装拉起支付参数
     *
     * @param time
     * @param prepayId
     * @return
     */
    private Map<String, Object> getPaymentData(String time, String prepayId) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isEmpty(prepayId)) {
            return map;
        }
        String nonceStr = UUID.randomUUID().toString();
        map.put("timeStamp", time);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", "RSA");
        String message = appPayProperties.getAppId() + "\n" + time + "\n" + nonceStr + "\n" + "prepay_id=" + prepayId + "\n";
        String paySign = sign(message);
        map.put("paySign", paySign);
        return map;
    }

    /**
     * 构建Authorization
     *
     * @param mchid
     * @param certificateSerialNo
     * @param method
     * @param uri
     * @param body
     * @return
     */
    public String buildAuthorization(String mchid, String certificateSerialNo,
                                     String method, String uri, String body) {
        String nonce = createNonce(32);
        long timestamp = Instant.now().getEpochSecond();

        String message = String.format("%s\n%s\n%d\n%s\n%s\n", method, uri, timestamp, nonce,
                body == null ? "" : body);

        String signature = sign(message);

        return String.format(
                "WECHATPAY2-SHA256-RSA2048 mchid=\"%s\",nonce_str=\"%s\",signature=\"%s\"," +
                        "timestamp=\"%d\",serial_no=\"%s\"",
                mchid, nonce, signature, timestamp, certificateSerialNo);
    }

    /**
     * 随机32位字符串
     */
    private static final char[] SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final SecureRandom random = new SecureRandom();

    public static String createNonce(int length) {
        char[] buf = new char[length];
        for (int i = 0; i < length; ++i) {
            buf[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }
        return new String(buf);
    }

    /**
     * 签名
     *
     * @param message
     * @return
     */
    public String sign(String message) {
        byte[] sign;
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            sign = signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("The current Java environment does not support " + algorithm, e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(algorithm + " signature uses an illegal privateKey.", e);
        } catch (SignatureException e) {
            throw new RuntimeException("An error occurred during the sign process.", e);
        }
        return Base64.getEncoder().encodeToString(sign);
    }

    private PrivateKey getPrivateKey() {
        InputStream resourceAsStream = WxController.class.getResourceAsStream("/keyFiles" + "/" + active + "/apiclient_key.pem");
        String privateContext = streamTostring(resourceAsStream);
        return PemUtil.loadPrivateKeyFromString(privateContext);
    }
}
