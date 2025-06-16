package mav.shan.payment.controller;

import mav.shan.common.entity.UserDTO;
import mav.shan.common.excel.UserEcxelVO;
import mav.shan.payment.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mav.shan.common.utils.EasyExcelUtils;
import mav.shan.common.utils.ResultUtils;
import mav.shan.common.vo.req.LoginReqVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static mav.shan.common.utils.JwtUtils.getToken;
import static mav.shan.common.utils.ResultUtils.success;


@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public ResultUtils<String> login(LoginReqVO reqVO) {
        UserDTO user = userService.login(reqVO);
        return success(getToken(user));
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
}
