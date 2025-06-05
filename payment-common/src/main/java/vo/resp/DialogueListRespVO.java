package vo.resp;

import lombok.Data;
import vo.req.DialogueReqVO;

import java.util.List;

@Data
public class DialogueListRespVO {
    List<DialogueReqVO> list;
}
