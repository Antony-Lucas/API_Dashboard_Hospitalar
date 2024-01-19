package com.knowbidash.knowbidash.streaming.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO;
import com.knowbidash.knowbidash.entities.oracle.models.AtendimentoFilterRequests;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KafkaProducer {
}
