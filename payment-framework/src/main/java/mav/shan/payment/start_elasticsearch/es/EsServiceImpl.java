package mav.shan.payment.start_elasticsearch.es;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class EsServiceImpl implements EsService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    public Boolean createIndexLibrary(XContentBuilder mappingTemplate, String indexName) {
        try {
            // Step 1: 创建 CreateIndexRequest 对象
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //创建request对象
            request.source(mappingTemplate);
            restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Boolean delIndexLibrary(String indexName) {
        //创建request对象
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        //发送请求
        try {
            restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Boolean exisIndexLibrary(String indexName) {
        //创建request对象
        GetIndexRequest request = new GetIndexRequest(indexName);
        //发送请求
        boolean exists = false;
        try {
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return exists;
    }

    public Boolean createDocument(String str) {

        return true;
    }

    //    @ApiOperation("搜索文档")
    public String querDocument(String id) {
        //创建request对象
        GetRequest request = new GetRequest("cust", id);
        //发送请求
        GetResponse response = null;
        try {
            response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String josn = response.getSourceAsString();
//        CustDTO custDTO = JSON.parseObject(josn, CustDTO.class);
        return null;
    }

    //    @ApiOperation("更新文档")
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

    //    @ApiOperation("删除文档")
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

    //    @ApiOperation("批量新增")
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

    //    @ApiOperation("Match根据地址查询文档")
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

    //    @ApiOperation("叶子查询-MatchAll")
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

    //    @ApiOperation("聚合查询-aggMatchAll")
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

    //    @ApiOperation("叶子查询-MatchOther")
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
