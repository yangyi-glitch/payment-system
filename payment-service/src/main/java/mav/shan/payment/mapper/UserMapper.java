package mav.shan.payment.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mav.shan.common.entity.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO> {
}
