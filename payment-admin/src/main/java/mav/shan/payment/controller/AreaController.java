package mav.shan.payment.controller;

import mav.shan.payment.config.adaptermode.ApplicationContext_Shan;
import mav.shan.payment.service.area.AreaService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
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
    public ResultUtils list() {
        return success(areaService.treeList());
    }
}
