package mav.shan.payment.es.useres;

import mav.shan.payment.start_elasticsearch.es.EsService;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class UserEsServiceImpl implements UserEsService {
    @Resource
    private EsService esService;

    @Override
    public Boolean createIndexLibrary() {
        try {
            Boolean aBoolean = esService.exisIndexLibrary("user");
            if (!aBoolean) {
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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Map<String, Object> getIndexMapping() {
        try {
            Boolean aBoolean = esService.exisIndexLibrary("user");
            if (aBoolean) {
                GetMappingsResponse mapping = esService.getIndexMapping("user");
                return mapping.mappings().get("user").sourceAsMap();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
