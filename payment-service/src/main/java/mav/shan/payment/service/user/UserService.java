package mav.shan.payment.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.UserDTO;
import vo.req.LoginReqVO;

public interface UserService extends IService<UserDTO> {
    UserDTO login(LoginReqVO reqVO);
}
