package mav.shan.payment.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.mapper.UserMapper;
import org.springframework.stereotype.Service;
import vo.req.LoginReqVO;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDTO> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(LoginReqVO reqVO) {
        UserDTO userDTO = userMapper.selectOne(new LambdaQueryWrapper<UserDTO>()
                .eq(UserDTO::getAccount, reqVO.getAccount())
                .eq(UserDTO::getPassword, reqVO.getPassword()));
        return userDTO;
    }
}
