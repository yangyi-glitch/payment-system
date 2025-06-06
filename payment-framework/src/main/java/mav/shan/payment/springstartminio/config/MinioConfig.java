package mav.shan.payment.springstartminio.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MinioConfig {
    private String endpoint = "http://127.0.0.1:9000";
    private String accesskey = "minioadmin";
    private String secretkey = "minioadmin";
    private String bucket = "localhost-file";

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accesskey, secretkey)
                .build();
    }
}
