package mav.shan.payment.es.useres;

import mav.shan.payment.springstartelasticsearch.es.EsService;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class UserEsServiceImpl implements UserEsService {
    @Resource
    private EsService esService;

    @Override
    public Boolean createIndexLibrary() {
        try {
            XContentBuilder mappingsBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("custName")
                    .field("type", "text")
                    .endObject()
                    .startObject("custAddress")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject();
            esService.createIndexLibrary(mappingsBuilder, "user");

            System.out.println(esService.exisIndexLibrary("user"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
