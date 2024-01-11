package com.knowbidash.knowbidash.streaming.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

public class KafkaListenner {
    @Autowired
    private AtendimentoPacienteVRepositories repositories;

    @KafkaListener(topics = "topic1")
    public void readDataStream(@Payload String record) {
        if(record!=null && record.length()>0) {
            AtendimentoPacienteV object = null;
            try {
                object = new ObjectMapper().readValue(record, AtendimentoPacienteV.class);
                repositories.save(object);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
