package mav.shan.payment.start_kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.start_kafka.config.KafkaConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Resource
    private KafkaConfig kafkaConfig;

    @Override
    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        // 创建 AdminClient 的配置
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConfig.getBootstrapServers());

        // 创建 AdminClient 实例
        try (AdminClient adminClient = AdminClient.create(props)) {
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor)
                    .configs(Collections.singletonMap(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE)); // 设置清理策略
            // 创建主题
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
            log.info("主题[{topicName}]已成功创建！");
        } catch (InterruptedException | ExecutionException e) {
            log.info("创建主题失败");
            e.getMessage();
            log.info("===============================");
            e.printStackTrace();
        }
    }
}
