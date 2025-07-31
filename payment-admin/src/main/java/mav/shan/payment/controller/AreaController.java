package mav.shan.payment.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mav.shan.payment.config.adaptermode.ApplicationContext_Shan;
import mav.shan.payment.service.area.AreaService;
import mav.shan.payment.start_redis.queue.RichListQueue;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mav.shan.common.utils.ResultUtils;

import javax.annotation.Resource;

import static mav.shan.common.utils.ResultUtils.success;

@RequestMapping("/area")
@RestController
public class AreaController {
    @Resource
    private AreaService areaService;
    @Resource
    private ApplicationContext_Shan shan;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RichListQueue richListQueue;

    @PostMapping("/create")
    public ResultUtils create() {
        areaService.create();
        return success(true);
    }

    @GetMapping("/list")
    public ResultUtils list() {
        String s = HttpUtil.get("https://restapi.amap.com/v3/geocode/regeo?key=820bb8c2b656661334bf6fb578fe7a43&location=116.34159,39.72684");
        JSONObject jsonObject = JSON.parseObject(s);
        String regeocode = jsonObject.getString("regeocode");
        JSONObject jsonObject1 = JSON.parseObject(regeocode);
        Object o = jsonObject1.get("formatted_address");
        System.out.println(o);
        return success(areaService.treeList());
    }
}
