package mav.shan.payment.controller;

import mav.shan.payment.annotation.Idempotent;
import mav.shan.payment.annotation.RateLimiter;
import mav.shan.payment.service.area.AreaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mav.shan.common.utils.ResultUtils;
import mav.shan.common.vo.resp.AreaRespVO;

import javax.annotation.Resource;
import java.util.List;

import static mav.shan.common.utils.ResultUtils.success;

@RequestMapping("/area")
@RestController
public class AreaController {
    @Resource
    private AreaService areaService;

    @PostMapping("/create")
    public ResultUtils create() {
        areaService.create();
        return success(true);
    }

    @GetMapping("/list")
    @Idempotent(message = "请勿重复获取地区列表，滚蛋~")
//    @RateLimiter
    public ResultUtils list() {
        List<AreaRespVO> list = areaService.treeList();
        return success(list);
    }
}
