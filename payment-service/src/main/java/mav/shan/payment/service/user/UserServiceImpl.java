package mav.shan.payment.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mav.shan.common.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.mapper.UserMapper;
import org.springframework.stereotype.Service;
import mav.shan.common.vo.req.LoginReqVO;

import javax.annotation.Resource;

import static mav.shan.common.constants.RedisConstants.USER_KEY_PREFIX;

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

    private String formatKey(String key) {
        return String.format(USER_KEY_PREFIX, key);
    }
}
