package vo.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import static constants.EsIndexNameConstant.USER_ES;

@Data
@Document(indexName = USER_ES, shards = 6)
public class UserEsVO {
    @Id
    @Field(type = FieldType.Keyword)
    private Long userId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String account;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String username;
    @Field(type = FieldType.Keyword)
    private String password;
    @Field(type = FieldType.Keyword)
    private String email;
    @Field(type = FieldType.Keyword)
    private String created;
    @Field(type = FieldType.Keyword)
    private String lastModified;
    @Field(type = FieldType.Keyword)
    private String role;
}
