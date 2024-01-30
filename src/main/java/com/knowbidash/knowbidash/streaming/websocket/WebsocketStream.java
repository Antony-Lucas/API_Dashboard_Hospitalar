package com.knowbidash.knowbidash.streaming.websocket;

import com.knowbidash.knowbidash.streaming.JSONDataService.AtendimentoDataService;
import com.knowbidash.knowbidash.streaming.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class WebsocketStream {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AtendimentoDataService dataService;
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    public WebsocketStream(SimpMessagingTemplate messagingTemplate, AtendimentoDataService dataService, KafkaProducer kafkaProducer) {
        this.messagingTemplate = messagingTemplate;
        this.dataService = dataService;
        this.kafkaProducer = kafkaProducer;
    }

    @MessageMapping("/stream")
    @SendTo("/topic/atendimentos")
    public String streamAtendimentoDataperWeek(@Payload Map<String, String> dateRange){
        try {
            LocalDateTime startData = LocalDateTime.parse(dateRange.get("startData"));
            LocalDateTime endData = LocalDateTime.parse(dateRange.get("endData"));

            System.out.println("startData" + startData);
            System.out.println("endData" + endData);

            String jsonObject = dataService.getAtendimentoPerMonth(startData, endData);
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            return "Erro ao obter dados de atendimento";
        }
    }
}
