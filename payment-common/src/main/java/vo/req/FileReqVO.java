package vo.req;

import lombok.Data;
import pojo.PagePOJO;

@Data
public class FileReqVO extends PagePOJO {
    private String fileName;
    private String fileType;
}
