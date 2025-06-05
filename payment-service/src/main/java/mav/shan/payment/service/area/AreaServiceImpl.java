package mav.shan.payment.service.area;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.AreaDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.mapper.AreaMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vo.resp.AreaRespVO;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaDTO> implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void create() {
        String filePath = "Z:/initTemplate/init/src/main/resources/area.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                AreaDTO area = new AreaDTO();
                area.setId(Long.valueOf(parts[0]));
                area.setName(parts[1]);
                area.setType(Integer.valueOf(parts[2]));
                area.setParentId(Long.valueOf(parts[3]));
                areaMapper.insert(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AreaRespVO> treeList() {
        String area = stringRedisTemplate.opsForValue().get("area");
        if (ObjectUtil.isNotEmpty(area)) {
            return JSONObject.parseArray(area, AreaRespVO.class);
        }
        List<AreaDTO> areaDTOS = areaMapper.selectList(new LambdaQueryWrapper<AreaDTO>()
                .eq(AreaDTO::getType, 2)
                .eq(AreaDTO::getParentId, 1));
        List<AreaRespVO> areaRespVOS = this.tree(switchBean(areaDTOS));
        stringRedisTemplate.opsForValue().set("area", JSONObject.toJSONString(areaRespVOS));
        return areaRespVOS;
    }

    private List<AreaRespVO> tree(List<AreaRespVO> areaDTOS) {
        if (CollectionUtils.isEmpty(areaDTOS)) {
            return areaDTOS;
        }
        for (AreaRespVO areaDTO : areaDTOS) {
            List<AreaDTO> children = areaMapper.selectList(new LambdaQueryWrapper<AreaDTO>()
                    .eq(AreaDTO::getParentId, areaDTO.getValue()));
            List<AreaRespVO> areaRespVOS = tree(switchBean(children));
            areaDTO.setChildren(areaRespVOS);
        }
        return areaDTOS;
    }

    private List<AreaRespVO> switchBean(List<AreaDTO> areaDTOS) {
        List<AreaRespVO> areaRespVOS = new ArrayList<>();
        for (AreaDTO areaDTO : areaDTOS) {
            AreaRespVO bean = new AreaRespVO();
            bean.setValue(areaDTO.getId());
            bean.setLabel(areaDTO.getName());
            areaRespVOS.add(bean);
        }
        return areaRespVOS;
    }
}
