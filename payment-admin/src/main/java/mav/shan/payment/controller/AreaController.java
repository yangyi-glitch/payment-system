package mav.shan.payment.controller;

import mav.shan.common.vo.resp.AreaRespVO;
import mav.shan.payment.annotation.Idempotent;
import mav.shan.payment.config.adaptermode.ApplicationContext_Shan;
import mav.shan.payment.service.area.AreaService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mav.shan.common.utils.ResultUtils;

import javax.annotation.Resource;

import java.util.List;

import static mav.shan.common.utils.ResultUtils.success;
import static mav.shan.payment.config.adaptermode.PayEnum.getPayByCode;

@RequestMapping("/area")
@RestController
public class AreaController {
    @Resource
    private AreaService areaService;
    @Resource
    private ApplicationContext_Shan shan;
    @Resource
    private RedissonClient redissonClient;
    @Value("${user-1.name}")
    private String name;
    @Value("${user-1.age}")
    private Integer age;

    @PostMapping("/create")
    public ResultUtils create() {
        areaService.create();
        return success(true);
    }

    @GetMapping("/list")
//    @Idempotent(message = "请勿重复获取地区列表，滚蛋~")
//    @RateLimiter(message = "请求太多了，滚蛋~")
    public ResultUtils list() {
        System.out.println(name);
        System.out.println(age);
//        shan.pay(getPayByCode(1));
//        List<AreaRespVO> list = areaService.treeList();
//        return success(list);
        return null;
    }
}
