package com.knowbidash.knowbidash.streaming.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "topic1", groupId = "knbi1")
    public void listen(@Payload  String message) {
        try {
            System.out.println("Received message: " + message);
            messagingTemplate.convertAndSend("/topic1", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
