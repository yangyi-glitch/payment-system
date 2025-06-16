package mav.shan.common.vo.resp;

import lombok.Data;

import java.util.List;

@Data
public class AreaRespVO {
    private String label;

    private Long value;

    private List<AreaRespVO> children;
}
