package com.knowbidash.knowbidash.streaming.JSONDataService;

import com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public String getAtendimentoPerMonth(LocalDateTime startDate, LocalDateTime endDate){
        JSONObject jsonResponse = new JSONObject();

        try {
            List<AtendimentoPacienteDTO> atendimentoPacienteV = atendimentoPacienteRepo.findTotalAtendimentosInPeriod(startDate, endDate);

            Map<String, Long> atendimentoPerMonthMap = new HashMap<>();
            Map<String, Long> atendimentoPerConvMap = new HashMap<>();

            for (AtendimentoPacienteDTO atendimentoPaciente : atendimentoPacienteV){
                String mesAno = String.format("%02d-%d", atendimentoPaciente.getMes(), atendimentoPaciente.getYear());

                // Somando valores para cada mês
                atendimentoPerMonthMap.put(mesAno, atendimentoPerMonthMap.getOrDefault(mesAno, 0L) + atendimentoPaciente.getTotal());

                // Somando valores para cada convênio
                String convenio = atendimentoPaciente.getDsConvenio();
                atendimentoPerConvMap.put(convenio, atendimentoPerConvMap.getOrDefault(convenio, 0L) + atendimentoPaciente.getTotal());
            }

            JSONArray atendimentoPacientePorMesArray = new JSONArray();
            for (Map.Entry<String, Long> entry : atendimentoPerMonthMap.entrySet()) {
                JSONObject atendimentoPorMes = new JSONObject();
                atendimentoPorMes.put("mes", entry.getKey());
                atendimentoPorMes.put("total", entry.getValue());
                atendimentoPacientePorMesArray.put(atendimentoPorMes);
            }
            jsonResponse.put("atendimentopormes", atendimentoPacientePorMesArray);

            JSONArray atendimentosPorConvenioArray = new JSONArray();
            for (Map.Entry<String, Long> entry : atendimentoPerConvMap.entrySet()){
                JSONObject atendimentoPorConvenio = new JSONObject();
                atendimentoPorConvenio.put("convenio", entry.getKey());
                atendimentoPorConvenio.put("total", entry.getValue());
                atendimentosPorConvenioArray.put(atendimentoPorConvenio);
            }
            jsonResponse.put("atendimentoporconvenio", atendimentosPorConvenioArray);
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonResponse.toString();
    }
}
