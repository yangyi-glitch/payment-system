package mav.shan.payment.start_elasticsearch.es;

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
     * @param indexName
     * @param data
     * @return
     */
    <T> Boolean createDocument(String indexName, T data);

    /**
     * 查询文档
     *
     * @param id
     * @return
     */
    String querDocumentByKeyWord(String indexName, String field, String id);

    /**
     * 修改文档
     *
     * @param indexName
     * @param data
     * @return
     */
    <T> Boolean updateDocument(String indexName, T data);

    /**
     * 删除文档
     *
     * @param indexName
     * @param value
     * @param field
     * @return
     */
    Boolean delDocument(String indexName, String field, String value);

    /**
     * 批量创建文档
     *
     * @return
     */
    <T> Boolean bulkDocument(String indexName, List<T> data);

    /**
     * 叶子查询
     *
     * @return
     */
    List<Object> matchAll(String indexName, Class clazz);

    /**
     * 匹配查询
     *
     * @return
     */
    List<Object> querDocumentByText(String userEs, String field, String fieldValue, Class clazz);
}
