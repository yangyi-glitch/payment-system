package mav.shan.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpGetDateUtil {

    //https://www.api-hub.cn/holidays
    //http://timor.tech/api/holiday
    public boolean isHolidayOrWorkday() {
        String url = "http://timor.tech/api/holiday/info";
        String response = HttpUtil.get(url); // GET 请求获取节假日信息
        JSONObject result = JSON.parseObject(response);

        if ("OK".equals(result.getString("status"))) {
            JSONObject holidayInfo = result.getJSONObject("holiday");

            if (holidayInfo == null) {
                System.out.println("今天不是节假日");
                return false;
            }

            Boolean isHoliday = holidayInfo.getBoolean("holiday");
            String holidayName = holidayInfo.getString("name");

            if (isHoliday != null && isHoliday) {
                System.out.println("今天是节假日：" + holidayName);
                return true;
            } else {
                System.out.println("今天是调休工作日");
                return false;
            }
        } else {
            System.out.println("获取节假日信息失败");
            return false;
        }
    }
}
