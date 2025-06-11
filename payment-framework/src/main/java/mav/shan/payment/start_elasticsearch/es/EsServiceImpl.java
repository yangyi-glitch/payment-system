package mav.shan.payment.start_elasticsearch.es;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EsServiceImpl implements EsService {

    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private ElasticsearchRestTemplate restTemplate;

    @Override
    public Boolean createIndexLibrary(Class clazz) {
        IndexOperations indexOperations = restTemplate.indexOps(clazz);
        System.out.println(indexOperations.exists());
        if (!indexOperations.exists()) {
            indexOperations.create();
            Document document = indexOperations.createMapping();
            indexOperations.putMapping(document);
            log.info("create index for LoginLog, document : {}", document);
        } else {
            log.info("index 已存在");
        }
        return true;
    }

    @Override
    public Boolean delIndexLibrary(Class clazz) {
        IndexOperations indexOperations = restTemplate.indexOps(clazz);
        if (indexOperations.exists()) {
            indexOperations.delete();
            log.info("delete index for LoginLog");
        } else {
            log.info("index 不存在");
        }
        return true;
    }

    @Override
    public Boolean exisIndexLibrary(Class clazz) {
        IndexOperations indexOperations = restTemplate.indexOps(clazz);
        return indexOperations.exists();
    }

    @Override
    public <T> Boolean createDocument(String indexName, T data) {
        // 将对象转为json
        String str = JSON.toJSONString(data);
        log.info("createDocument===>json: [{}]", str);
        // 创建索引请求对象
        IndexRequest indexRequest = new IndexRequest(indexName).source(str, XContentType.JSON);
        // 执行增加文档
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String querDocumentByKeyWord(String indexName, String field, String value) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(field, value.toString()));
        //设置查询索引
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = searchResponse.getHits().getHits();
            if (hits != null && hits.length > 0) {
                SearchHit searchHit = hits[0];
                return searchHit.getSourceAsString();
            }
        } catch (Exception e) {
            log.error("查询消息失败！");
        }
        return null;
    }

    @Override
    public <T> Boolean updateDocument(String indexName, T data) {
//        this.querDocumentByKeyWord(indexName, "userId", ((UserEsVO) data).getUserId());
//        this.createDocument(indexName, data);
        return true;
    }

    @Override
    public Boolean delDocument(String indexName, String field, String value) {
        try {
            // 创建DeleteByQueryRequest对象
            DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
            // 设置查询条件，例如删除所有年龄大于30的文档
            request.setQuery(QueryBuilders.matchPhraseQuery(field, value));
            //发送请求
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public <T> Boolean bulkDocument(String indexName, List<T> data) {
        if (data == null || data.size() == 0){
            return false;
        }
        try {
            //创建request对象
            BulkRequest request = new BulkRequest();
            for (T datum : data) {
                request.add(new IndexRequest(indexName).source(JSON.toJSONString(datum), XContentType.JSON));
            }
            //发送请求
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Object> querDocumentByText(String indexName, String field, String value, Class clazz) {
        List<Object> data = new ArrayList<>();
        //创建request对象
        SearchRequest request = new SearchRequest(indexName);
        request.source().query(QueryBuilders.matchQuery(field, value));
        //发送请求
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            //解析
            SearchHits searchHits = response.getHits();
            SearchHit[] hits = searchHits.getHits();
            for (SearchHit e : hits) {
                String source = e.getSourceAsString();
                Object o = JSON.parseObject(source, clazz);
                data.add(o);
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> matchAll(String indexName, Class clazz) {
        List<Object> data = new ArrayList<>();
        //创建request对象
        SearchRequest request = new SearchRequest(indexName);
        request.source().query(QueryBuilders.matchAllQuery()).size(200);
        try {
            SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            for (SearchHit e : hits) {
                String source = e.getSourceAsString();
                Object o = JSON.parseObject(source, clazz);
                data.add(o);
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
