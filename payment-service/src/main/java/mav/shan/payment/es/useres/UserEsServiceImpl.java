package mav.shan.payment.es.useres;

import cn.hutool.core.bean.BeanUtil;
import entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.start_elasticsearch.es.EsService;
import org.springframework.stereotype.Service;
import vo.es.UserEsVO;

import javax.annotation.Resource;

import static constants.EsIndexNameConstant.USER_ES;

@Slf4j
@Service
public class UserEsServiceImpl implements UserEsService {
    @Resource
    private EsService esService;

    @Override
    public Boolean createIndexLibrary() {
        if (!esService.exisIndexLibrary(UserEsVO.class)) {
            esService.createIndexLibrary(UserEsVO.class);
        }
        return true;
    }

    @Override
    public Boolean createDocument(UserDTO user) {
        UserEsVO bean = BeanUtil.toBean(user, UserEsVO.class);
        if (esService.exisIndexLibrary(UserEsVO.class)) {
            esService.createDocument(USER_ES, bean);
        }
        return true;
    }

    @Override
    public UserDTO querDocument(Long userId) {
        UserEsVO userEsVO = esService.querDocument(USER_ES, "userId", userId);
        return BeanUtil.toBean(userEsVO, UserDTO.class);
    }

    @Override
    public Boolean delIndexLibrary() {
        return esService.delIndexLibrary(UserEsVO.class);
    }
}
