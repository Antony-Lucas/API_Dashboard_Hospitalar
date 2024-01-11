package com.knowbidash.knowbidash.streaming.JSONDataService;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AtendimentoDataService {
    @Autowired
    private AtendimentoPacienteVRepositories atendimentoPacienteRepo;

    public String getAtendimentos(LocalDateTime startData, LocalDateTime endData){
        JSONObject jsonResponse = new JSONObject();

        try {
            List<AtendimentoPacienteV> atendimentoPacienteV = atendimentoPacienteRepo.findByDtEntradaBetween(startData, endData);

            JSONArray atendimentoPacienteArray = new JSONArray();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (AtendimentoPacienteV atendimentoPaciente: atendimentoPacienteV){
                JSONObject atendimentoPacienteJson = new JSONObject();
                atendimentoPacienteJson.put("label", atendimentoPaciente.getNmMedico());
                atendimentoPacienteJson.put("data", atendimentoPaciente.getDtEntrada().format(formatter));
                atendimentoPacienteArray.put(atendimentoPacienteJson);
            }

            jsonResponse.put("AtendimentoPacienteMes", atendimentoPacienteArray);
        } catch (Exception e){
            e.printStackTrace();
        }

        return jsonResponse.toString();
    }
}
