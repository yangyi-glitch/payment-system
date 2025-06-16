package mav.shan.common.vo.resp;

import lombok.Data;
import mav.shan.common.vo.req.DialogueReqVO;

import java.util.List;

@Data
public class DialogueListRespVO {
    List<DialogueReqVO> list;
}
