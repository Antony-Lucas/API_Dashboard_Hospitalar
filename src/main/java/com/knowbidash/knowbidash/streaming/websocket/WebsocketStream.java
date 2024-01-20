package com.knowbidash.knowbidash.streaming.websocket;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.FiltroData;
import com.knowbidash.knowbidash.entities.oracle.models.AtendimentoFilterRequests;
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
    @Value("/topic1")
    private String stompTopic;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AtendimentoDataService dataService;
    @Autowired
    private KafkaProducer kafkaProducer;

    @MessageMapping("/stream")
    public void streamAtendimentoDataperWeek(@Payload Map<String, String> dateRange){
        try {
            LocalDate startData = LocalDate.parse(dateRange.get("startData"));
            LocalDate endData = LocalDate.parse(dateRange.get("endData"));

            System.out.println("startData" + startData);
            System.out.println("endData" + endData);

            String jsonObject = dataService.getAtendimentoPerMonth(startData, endData);
            messagingTemplate.convertAndSend(stompTopic, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@MessageMapping("/atendimento")
    @SendTo("/topic1")
    public void streamAtendimentoPerMonth(@Payload AtendimentoFilterRequests requests){
        try{
            String jsonObject = dataService.getAtendimentoPerMonth(requests.getStartDate(), requests.getEndDate());
            messagingTemplate.convertAndSend(stompTopic, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
