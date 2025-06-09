package mav.shan.payment.es.useres;

import java.util.Map;

public interface UserEsService {
    Boolean createIndexLibrary();

    Map<String,Object> getIndexMapping();
}
