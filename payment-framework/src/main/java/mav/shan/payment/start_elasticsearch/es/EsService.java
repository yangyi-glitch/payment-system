package mav.shan.payment.start_elasticsearch.es;


import vo.es.UserEsVO;

import java.util.List;

public interface EsService {
    /**
     * 创建索引库
     *
     * @return
     */
    Boolean createIndexLibrary(Class clazz);

    /**
     * 删除索引库
     *
     * @return
     */
    Boolean delIndexLibrary(Class clazz);

    /**
     * 判断索引库是否存在
     *
     * @return
     */
    Boolean exisIndexLibrary(Class clazz);

    /**
     * 创建文档
     *
     * @param T
     * @return
     */
    <T> Boolean createDocument(String indexName,T data);

    /**
     * 查询文档
     *
     * @param id
     * @return
     */
    UserEsVO querDocument(String indexName, String field, Long id);

    /**
     * 修改文档
     *
     * @param id
     * @param name
     * @param address
     * @return
     */
    Boolean updateDocument(String id, String name, String address);

    /**
     * 删除文档
     *
     * @param id
     * @return
     */
    Boolean delDocument(String id);

    /**
     * 批量创建文档
     *
     * @return
     */
    Boolean bulkDocument();

    /**
     * 匹配查询
     *
     * @param address
     * @return
     */
    List<String> matchDocument(String address);

    /**
     * 叶子查询
     *
     * @return
     */
    List<String> MatchAll();

    /**
     * 聚合查询
     *
     * @return
     */
    List<String> aggMatchAll();

    /**
     * 跨字段匹配查询
     *
     * @return
     */
    List<String> MatchOther();
}
