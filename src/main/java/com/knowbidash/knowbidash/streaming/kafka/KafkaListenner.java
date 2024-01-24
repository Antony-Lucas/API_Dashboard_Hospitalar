package com.knowbidash.knowbidash.streaming.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenner {
    /*@Autowired
    private AtendimentoPacienteVRepositories repositories;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "/topic1")
    public void readDataStream(@Payload String record) {
        System.out.println("Received Kafka message: " + record);
        if(record!=null && record.length()>0) {
            System.out.println("Received Kafka message after not null: " + record);
            AtendimentoPacienteV object = null;
            try {
                object = new ObjectMapper().readValue(record, AtendimentoPacienteV.class);
                repositories.save(object);

                simpMessagingTemplate.convertAndSend("/topic1", "Banco de dados atualizado");
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
