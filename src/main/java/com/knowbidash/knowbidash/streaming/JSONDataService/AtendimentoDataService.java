package com.knowbidash.knowbidash.streaming.JSONDataService;

import com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtendimentoDataService {
    @Autowired
    private AtendimentoPacienteVRepositories atendimentoPacienteRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public String getAtendimentoPerMonth(LocalDate startDate, LocalDate endDate){
        JSONObject jsonResponse = new JSONObject();

        try{
            List<AtendimentoPacienteDTO> atendimentoPacienteV = atendimentoPacienteRepo.findTotalByMonthsInPeriod(startDate, endDate);

            JSONArray atendimentoPacientePorMesArray = new JSONArray();
            DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
            for (AtendimentoPacienteDTO atendimentoPaciente: atendimentoPacienteV){
                JSONObject atendimentoPerMonth = new JSONObject();
                String monthYear = String.format("%02d-%d", atendimentoPaciente.getMes(), atendimentoPaciente.getYear());
                atendimentoPerMonth.put("mes", monthYear);
                atendimentoPerMonth.put("total", atendimentoPaciente.getTotal());
                atendimentoPacientePorMesArray.put(atendimentoPerMonth);
            }

            jsonResponse.put("atendimentopormes", atendimentoPacientePorMesArray);
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonResponse.toString();
    }

    public String getAtendimentos(LocalDateTime startData, LocalDateTime endData){
        JSONObject jsonResponse = new JSONObject();

        try {
            List<AtendimentoPacienteV> atendimentoPacienteV = atendimentoPacienteRepo.findByDtEntradaBetween(startData, endData);
            //retorna dados gerais dos atendimentos
            JSONArray atendimentoPacienteArray = new JSONArray();
            for (AtendimentoPacienteV atendimentoPaciente: atendimentoPacienteV){
                JSONObject atendimentoPacienteJson = new JSONObject();
                atendimentoPacienteJson.put("label", atendimentoPaciente.getNmMedico());
                atendimentoPacienteJson.put("data", atendimentoPaciente.getDtEntrada());
                atendimentoPacienteArray.put(atendimentoPacienteJson);
            }

            jsonResponse.put("AtendimentoPaciente", atendimentoPacienteArray);
            //
        } catch (Exception e){
            e.printStackTrace();
        }

        return jsonResponse.toString();
    }
}
