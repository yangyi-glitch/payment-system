package mav.shan.payment.start_kafka.kafka;

public interface KafkaService {
    /**
     * 创建主题
     *
     * @param topicName      主题名称
     * @param numPartitions  分区数量
     * @param replicationFactor  副本数量
     */
    void createTopic(String topicName, int numPartitions, short replicationFactor);
}
