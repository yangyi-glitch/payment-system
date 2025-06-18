package mav.shan.payment.controller;

import mav.shan.common.entity.UserDTO;
import mav.shan.common.excel.UserEcxelVO;
import mav.shan.payment.service.esservice.useres.UserEsService;
import mav.shan.payment.service.user.UserService;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/createIndexLibrary")
    public ResultUtils<Boolean> createIndexLibrary() {
        userEsService.createIndexLibrary();
        return success(true);
    }

    @PostMapping("/createDocument")
    public ResultUtils<Boolean> createDocument(@RequestParam() Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setAccount("admin");
        userDTO.setUsername("管理员ABCD中国银行");
        userDTO.setPassword("<PASSWORD>");
        userDTO.setRole("1");
        userDTO.setEmail("<EMAIL>");
        userDTO.setCreated("2021-01-01 00:00:00");
        userDTO.setLastModified("2021-01-01 00:00:00");
        userEsService.createDocument(userDTO);
        return success(true);
    }

    @PostMapping("/delIndexLibrary")
    public ResultUtils<Boolean> delIndexLibrary() {
        userEsService.delIndexLibrary();
        return success(true);
    }

    @GetMapping("/querDocumentByKeyWord")
    public ResultUtils<UserDTO> querDocumentByKeyWord(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userEsService.querDocumentByKeyWord(userId);
        return success(userDTO);
    }

    @GetMapping("/querDocumentByText")
    public ResultUtils<List<UserDTO>> querDocumentByText(@RequestParam("fieldValue") String fieldValue) {
        return success(userEsService.querDocumentByText(fieldValue));
    }

    @GetMapping("/matchAll")
    public ResultUtils<List<UserDTO>> matchAll() {
        List<UserDTO> userDTOS = userEsService.matchAll();
        return success(userDTOS, Long.valueOf(userDTOS.size()));
    }

    @PostMapping("/updatateDocument")
    public ResultUtils<Boolean> updatateDocument(@RequestParam("userId") Long userId) {
        return success(userEsService.updatateDocument(userId));
    }

    @PostMapping("/delDocument")
    public ResultUtils<Boolean> delDocument(@RequestParam("userId") Long userId) {
        return success(userEsService.delDocument(userId));
    }

    @PostMapping("/bulkDocument")
    public ResultUtils<Boolean> bulkDocument() {
        return success(userEsService.bulkDocument());
    }
}
