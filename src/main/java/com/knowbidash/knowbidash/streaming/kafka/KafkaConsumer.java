package com.knowbidash.knowbidash.streaming.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private String latestMessage = "";
    @KafkaListener(topics = "/topic1")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        messagingTemplate.convertAndSend("/topic/kafka-messages", message);
        latestMessage = message;
    }

    public String getLatestMessage(){
        return latestMessage;
    }
}
