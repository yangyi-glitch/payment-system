package mav.shan.payment.es.useres;

import entity.UserDTO;

import java.util.List;

public interface UserEsService {
    /**
     * 创建索引库
     *
     * @return
     */
    Boolean createIndexLibrary();

    /**
     * 创建文档
     *
     * @param user
     * @return
     */
    Boolean createDocument(UserDTO user);

    /**
     * 获取文档
     *
     * @param userId
     * @return
     */
    UserDTO querDocumentByKeyWord(Long userId);

    /**
     * 删除索引库
     *
     * @return
     */
    Boolean delIndexLibrary();

    /**
     * 通过文本查询文档
     *
     * @param fieldValue
     * @return
     */
    List<UserDTO> querDocumentByText(String fieldValue);

    /**
     * 获取所有文档
     *
     * @return
     */
    List<UserDTO> matchAll();

    /**
     * 更新文档
     *
     * @param id
     * @return
     */
    Boolean updatateDocument(Long id);

    /**
     * 删除文档
     *
     * @param id
     * @return
     */
    Boolean delDocument(Long id);

    /**
     * 批量创建文档
     *
     * @return
     */
    Boolean bulkDocument();
}
