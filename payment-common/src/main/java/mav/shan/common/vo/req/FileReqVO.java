package mav.shan.common.vo.req;

import lombok.Data;
import mav.shan.common.pojo.PagePOJO;

@Data
public class FileReqVO extends PagePOJO {
    private String fileName;
    private String fileType;
}
