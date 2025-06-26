package mav.shan.payment.controller.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ListenerService {

    @KafkaListener(topics = "yangyi-6", groupId = "yangyi-3")
    public void listenerA(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        System.out.println("listenerA-消费");
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String value = record.value();
            System.out.println(value);
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "yangyi-6", groupId = "yangyi-3")
    public void listenerB(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        System.out.println("listenerB-消费");
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String value = record.value();
            System.out.println(value);
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "yangyi-6", groupId = "yangyi-3")
    public void listenerC(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        System.out.println("listenerC-消费");
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String value = record.value();
            System.out.println(value);
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "yangyi-1", groupId = "yangyi-3")
    public void listenerD(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        System.out.println("listenerD-消费yangyi-1");
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String value = record.value();
            System.out.println(value);
        }
        ack.acknowledge();
    }

    @KafkaListener(topics = "yangyi-1", groupId = "yangyi-4")
    public void listenerE(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        System.out.println("listenerE-消费yangyi-1");
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String value = record.value();
            System.out.println(value);
        }
        ack.acknowledge();
    }
}
