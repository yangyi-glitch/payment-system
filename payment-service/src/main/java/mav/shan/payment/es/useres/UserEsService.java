package mav.shan.payment.es.useres;

import entity.UserDTO;

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
    UserDTO querDocument(Long userId);

    /**
     * 删除索引库
     *
     * @return
     */
    Boolean delIndexLibrary();
}
