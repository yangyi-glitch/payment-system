package mav.shan.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class PayUtil {


    public static String alicreateOrderID() {
        return aligetTime() + aligetRandom(6);
    }

    public static String aligetTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }

    public static String aligetRandom(int len) {
        StringBuffer flag = new StringBuffer();
        String sources = "0123456789";
        Random rand = new Random();
        for (int j = 0; j < len; j++) {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return flag.toString();
    }

    /**
     * 生成待签名数据
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String builderSignStr(Map<String, Object> params, String md5key) throws Exception {
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append(key);
            sb.append("=");
            sb.append(params.get(key));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1); //去掉最后一个&
        sb.append(md5key);
        log.info("builderSignStr= " + sb.toString());
        log.info("验证sign:" + MD5(sb.toString()).toUpperCase());
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 验证公共参数完整性
     *
     * @param requestmap
     * @return
     */
    public static boolean verifyParameter(Map<String, Object> requestmap) {
        if (!requestmap.get("mid").equals("") && !requestmap.get("tid").equals("")
                && !requestmap.get("instMid").equals("") && !requestmap.get("msgSrc").equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 元转分
     * 1.23-->123
     * 银联商务微信支付中，"分"为无小数点类型
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) {
        BigDecimal price = BigDecimal.valueOf(new Double(amount)).multiply(new BigDecimal(100));
        DecimalFormat df2 = new DecimalFormat("#");  //定义分格式
        String str2 = df2.format(price);
        return str2;
    }

    /**
     * 元转分
     * 1.23-->123.00
     * 转化后保留小数点后两位
     *
     * @param amount
     * @return
     */
    public static String wxchangeY2F(String amount) {

        return BigDecimal.valueOf(new Double(amount)).multiply(new BigDecimal(100)).toString();
    }


    /**
     * 金额分转元
     */
    /**
     * 将字符串"分"转换成"元"（长格式），如：100分被转换为1.00元。
     *
     * @param s
     * @return
     */
    public static String convertCent2Dollar(String s) {
        if ("".equals(s) || s == null) {
            return "";
        }
        long l;
        if (s.length() != 0) {
            if (s.charAt(0) == '+') {
                s = s.substring(1);
            }
            l = Long.parseLong(s);
        } else {
            return "";
        }
        boolean negative = false;
        if (l < 0) {
            negative = true;
            l = Math.abs(l);
        }
        s = Long.toString(l);
        if (s.length() == 1) {
            return (negative ? ("-0.0" + s) : ("0.0" + s));
        }
        if (s.length() == 2) {
            return (negative ? ("-0." + s) : ("0." + s));
        } else {
            return (negative ? ("-" + s.substring(0, s.length() - 2) + "." + s
                    .substring(s.length() - 2)) : (s.substring(0,
                    s.length() - 2)
                    + "." + s.substring(s.length() - 2)));
        }
    }

    /**
     * 将字符串"分"转换成"元"（短格式），如：100分被转换为1元。
     *
     * @param s
     * @return
     */
    public static String changeF2Y(String s) {
        String ss = convertCent2Dollar(s);
        ss = "" + Double.parseDouble(ss);
        if (ss.endsWith(".0")) {
            return ss.substring(0, ss.length() - 2);
        }
        if (ss.endsWith(".00")) {
            return ss.substring(0, ss.length() - 3);
        } else {
            return ss;
        }
    }

    /**
     * 生成待签名数据
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String builderSHA256SignStr(Map<String, String> params, String md5key) throws Exception {
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append(key);
            sb.append("=");
            sb.append(params.get(key));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1); //去掉最后一个&
        sb.append(md5key);
        log.info("builderSHA256SignStr= " + sb.toString());
        String signStr = encodeSHA256(md5key, sb.toString()).toUpperCase();
        log.info("验证sign:" + signStr);
        return signStr;
    }

    public static String encodeSHA256(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            hmac.init(secret_key);
            return new String(Hex.encodeHex(hmac.doFinal(data.getBytes("UTF-8"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String ss = "billDate=2017-06-26&billNo=31940000201700002&goods=[{\"body\":\"微信二维码测试\",\"price\":\"1\",\"goodsName\":\"微信二维码测试\",\"goodsId\":\"1\",\"quantity\":\"1\",\"goodsCategory\":\"TEST\"}]&instMid=QRPAYDEFAULT&mid=898340149000005&msgSrc=WWW.TEST.COM&msgType=bills.getQRCode&requestTimestamp=2017-06-26 17:28:02&tid=88880001&totalAmount=1&walletOption=SINGLEfcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR";
        String key = "fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR";
        System.out.println(encodeSHA256(key, ss));
    }

}
