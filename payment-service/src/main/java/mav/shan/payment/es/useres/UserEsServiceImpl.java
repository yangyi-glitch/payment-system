package mav.shan.payment.es.useres;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.start_elasticsearch.es.EsService;
import org.springframework.stereotype.Service;
import vo.es.UserEsVO;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

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
    public UserDTO querDocumentByKeyWord(Long userId) {
        String data = esService.querDocumentByKeyWord(USER_ES, "userId", String.valueOf(userId));
        UserEsVO esVO = JSON.parseObject(data, UserEsVO.class);
        return BeanUtil.toBean(esVO, UserDTO.class);
    }

    @Override
    public Boolean delIndexLibrary() {
        return esService.delIndexLibrary(UserEsVO.class);
    }

    @Override
    public List<UserDTO> querDocumentByText(String fieldValue) {
        List<Object> data = esService.querDocumentByText(USER_ES, "userId", fieldValue, UserEsVO.class);
        List<UserEsVO> userEsVOS = BeanUtil.copyToList(data, UserEsVO.class);
        List<UserDTO> userDTOS = BeanUtil.copyToList(userEsVOS, UserDTO.class);
        return userDTOS;
    }

    @Override
    public List<UserDTO> matchAll() {
        List<Object> data = esService.matchAll(USER_ES, UserEsVO.class);
        List<UserEsVO> userEsVOS = BeanUtil.copyToList(data, UserEsVO.class);
        List<UserDTO> userDTOS = BeanUtil.copyToList(userEsVOS, UserDTO.class);
        return userDTOS;
    }

    @Override
    public Boolean delDocument(Long id) {
        return esService.delDocument(USER_ES, "userId", String.valueOf(id));
    }

    @Override
    public Boolean updatateDocument(Long id) {
        String data = esService.querDocumentByKeyWord(USER_ES, "userId", String.valueOf(id));
        UserEsVO esVO = JSON.parseObject(data, UserEsVO.class);
        if (ObjectUtil.isNull(esVO)) {
            return true;
        }
        Boolean b = esService.delDocument(USER_ES, "userId", String.valueOf(id));
        if (b) {
            esVO.setUsername("草拟审查vv啊vv饿哦啊");
            esService.createDocument(USER_ES, esVO);
        }
        return true;
    }

    @Override
    public Boolean bulkDocument() {
        List<UserEsVO> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserEsVO userEsVO = new UserEsVO();
            userEsVO.setUserId(Long.valueOf(i));

            String data = esService.querDocumentByKeyWord(USER_ES, "userId", String.valueOf(i));
            UserEsVO esVO = JSON.parseObject(data, UserEsVO.class);
            if (ObjectUtil.isNotEmpty(esVO)) {
                continue;
            }

            userEsVO.setAccount("account.中国" + i);
            userEsVO.setUsername("username.中国" + i);
            userEsVO.setPassword("password" + i);
            userEsVO.setEmail("email" + i);
            userEsVO.setCreated("created" + i);
            userEsVO.setLastModified("lastModified" + i);
            list.add(userEsVO);
        }
        return esService.bulkDocument(USER_ES, list);
    }
}
