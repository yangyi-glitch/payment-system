package mav.shan.payment.service.area;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.AreaDTO;
import vo.resp.AreaRespVO;

import java.util.List;

public interface AreaService extends IService<AreaDTO> {
    void create();

    List<AreaRespVO> treeList();
}
