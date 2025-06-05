package mav.shan.payment.controller;

import mav.shan.payment.service.area.AreaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultUtils;
import vo.resp.AreaRespVO;

import javax.annotation.Resource;
import java.util.List;

import static utils.ResultUtils.success;

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
    public ResultUtils list() {
        List<AreaRespVO> list = areaService.treeList();
        return success(list);
    }
}
