package mav.shan.payment.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import mav.shan.common.entity.UserDTO;
import mav.shan.common.vo.req.LoginReqVO;

public interface UserService extends IService<UserDTO> {
    UserDTO login(LoginReqVO reqVO);
}
