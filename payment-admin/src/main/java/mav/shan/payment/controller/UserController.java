package mav.shan.payment.controller;

import entity.UserDTO;
import excel.UserEcxelVO;
import mav.shan.payment.es.useres.UserEsService;
import mav.shan.payment.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.EasyExcelUtils;
import utils.ResultUtils;
import vo.req.LoginReqVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static utils.JwtUtils.getToken;
import static utils.ResultUtils.success;


@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserEsService userEsService;

    @GetMapping("/login")
    public ResultUtils<String> login(LoginReqVO reqVO) {
        UserDTO login = userService.login(reqVO);
        return success(getToken(login));
    }

    @GetMapping("/list")
    public ResultUtils<List<UserDTO>> list() {
        List<UserDTO> list = userService.list();
        return success(list);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        EasyExcelUtils.write(response, "用户列表", UserEcxelVO.class, userService.list());
    }

    @GetMapping("/esTest")
    public ResultUtils<Boolean> createIndexLibrary() {
        userEsService.createIndexLibrary();
        return success(true);
    }

    @GetMapping("/esTest-1")
    public ResultUtils<Map<String,Object>> getIndexMapping() {
        return success(userEsService.getIndexMapping());
    }
}
