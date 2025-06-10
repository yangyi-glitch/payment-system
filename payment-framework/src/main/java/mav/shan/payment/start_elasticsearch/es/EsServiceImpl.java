package mav.shan.payment.start_elasticsearch.es;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;
import vo.es.UserEsVO;

import javax.annotation.Resource;
import java.io.IOException;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public UserEsVO querDocument(String indexName, String field, Long id) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(field, id.toString()));
        //设置查询索引
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = searchResponse.getHits().getHits();
            if (hits != null && hits.length > 0) {
                SearchHit searchHit = hits[0];
                return JSON.parseObject(searchHit.getSourceAsString(), UserEsVO.class);
            }
        } catch (Exception e) {
            log.error("查询消息失败！");
            return null;
        }
        return null;
    }

    @Override
    public Boolean updateDocument(String id, String name, String address) {
        //创建request对象
        UpdateRequest request = new UpdateRequest("cust", id);
        request.doc(
                "custName", name,
                "custAddress", address
        );
        //发送请求
        try {
            restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean delDocument(String id) {
        //创建request对象
        DeleteRequest request = new DeleteRequest("cust", id);
        //发送请求
        try {
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean bulkDocument() {
//        //创建request对象
//        BulkRequest request = new BulkRequest();
//        List<Customer> all = customerRs.findAll();
//        for (Customer e : all) {
//            CustDTO custDTO = JSONUtil.toBean(JSONUtil.toJsonStr(e), CustDTO.class);
//            request.add(new IndexRequest("cust").id(e.getCustId().toString()).source(JSON.toJSONString(custDTO), XContentType.JSON));
//        }
//        //发送请求
//        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return true;
    }

    @Override
    public List<String> matchDocument(String address) {
        //创建request对象
        SearchRequest request = new SearchRequest("cust");
        request.source().query(QueryBuilders.matchQuery("custAddress", address));
        //发送请求
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //解析
//        SearchHits searchHits = response.getHits();
//        SearchHit[] hits = searchHits.getHits();
//        for (SearchHit e : hits) {
//            String source = e.getSourceAsString();
//            CustDTO custDTO = JSON.parseObject(source, CustDTO.class);
//            custDTOS.add(custDTO);
//        }
        return null;
    }

    @Override
    public List<String> MatchAll() {
        //创建request对象
        SearchRequest request = new SearchRequest("cust");
        request.source().query(QueryBuilders.matchAllQuery()).size(20);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit e : hits) {
//            String source = e.getSourceAsString();
//            CustDTO custDTO = JSON.parseObject(source, CustDTO.class);
//            custDTOS.add(custDTO);
        }
        return null;
    }

    @Override
    public List<String> aggMatchAll() {
        //创建request对象
        SearchRequest request = new SearchRequest("cust");
        request.source().aggregation(AggregationBuilders.terms("aggNames").field("custName").size(20));
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Aggregations aggregations = search.getAggregations();
        Terms aggNames = aggregations.get("aggNames");
        List<? extends Terms.Bucket> buckets = aggNames.getBuckets();
        Terms.Bucket bucket = buckets.get(0);
        String keyAsString = bucket.getKeyAsString();
        long docCount = bucket.getDocCount();
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
//        for (SearchHit e : hits) {
//            String source = e.getSourceAsString();
//            CustDTO custDTO = JSON.parseObject(source, CustDTO.class);
//            custDTOS.add(custDTO);
//        }
        for (SearchHit documentFields : hits1) {
//            String sourceAsString = documentFields.getSourceAsString();
//            CustDTO custDTO = JSONUtil.toBean(sourceAsString, CustDTO.class);
//            custDTOS.add(custDTO);
        }
        return null;
    }

    @Override
    public List<String> MatchOther() {
        //创建request对象
        SearchRequest request = new SearchRequest("cust");
        request.source().query(QueryBuilders.matchQuery("custAddress", "北京"));
        request.source().highlighter(SearchSourceBuilder.highlight()
                        .field("custAddress")
                        .postTags("<em>")
                        .preTags("</em>"))
                .size(20);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit e : hits1) {
//            String source = e.getSourceAsString();
//            Map<String, HighlightField> highlightFields = e.getHighlightFields();
//            HighlightField name = highlightFields.get("custAddress");
//            CustDTO custDTO = JSON.parseObject(source, CustDTO.class);
//            String s = name.getFragments()[0].string();
//            custDTO.setCustAddress(s);
//            custDTOS.add(custDTO);
        }
        return null;
    }
}
