package com.knowbidash.knowbidash.streaming.websocket;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.FiltroData;
import com.knowbidash.knowbidash.streaming.JSONDataService.AtendimentoDataService;
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

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class WebsocketStream {
    @Value("/topic1")
    private String stompTopic;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AtendimentoDataService dataService;

    /*@Scheduled(fixedRate = 2000)
    public void streamAtendimentoDataperWeek(){
        try {
            LocalDateTime startData = LocalDateTime.now().minusDays(7);
            LocalDateTime endData = LocalDateTime.now();

            String jsonObject = dataService.getAtendimentos(startData, endData);
            messagingTemplate.convertAndSend(stompTopic, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @Scheduled(fixedRate = 2000)
    public void streamAtendimentoPerMonth(){
        try{
            String jsonObject = dataService.getAtendimentoPerMonth();
            messagingTemplate.convertAndSend(stompTopic, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
