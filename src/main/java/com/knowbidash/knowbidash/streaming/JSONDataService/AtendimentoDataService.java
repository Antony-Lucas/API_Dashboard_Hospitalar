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
            Map<String, Long> atendimentoPerClinicaMap = new HashMap<>();
            Map<String, Long> atendimentoPerMedicoMap = new HashMap<>();
            Map<String, Long> atendimentoPerSetorMap = new HashMap<>();
            Map<String, Long> atendimentoPerIdadeMap = new HashMap<>();

            for (AtendimentoPacienteDTO atendimentoPaciente : atendimentoPacienteV){
                String mesAno = String.format("%02d-%d", atendimentoPaciente.getMes(), atendimentoPaciente.getYear());

                // Somando valores para cada mês
                atendimentoPerMonthMap.put(mesAno, atendimentoPerMonthMap.getOrDefault(mesAno, 0L) + atendimentoPaciente.getTotal());
                // Somando valores para cada convênio
                String convenio = atendimentoPaciente.getDsConvenio();
                atendimentoPerConvMap.put(convenio, atendimentoPerConvMap.getOrDefault(convenio, 0L) + atendimentoPaciente.getTotal());
                // Somando valores por clinica de atendimento
                String clinica = atendimentoPaciente.getDsClinica();
                atendimentoPerClinicaMap.put(clinica, atendimentoPerClinicaMap.getOrDefault(clinica, 0L) + atendimentoPaciente.getTotal());
                // Somando numero de atendimentos por medico
                String medico =  atendimentoPaciente.getNmMedico();
                atendimentoPerMedicoMap.put(medico, atendimentoPerMedicoMap.getOrDefault(medico, 0L) + atendimentoPaciente.getTotal());
                // Somando numero de atendimentos por setor
                String setor = atendimentoPaciente.getDsSetorAtendimento();
                atendimentoPerSetorMap.put(setor, atendimentoPerSetorMap.getOrDefault(setor, 0L) + atendimentoPaciente.getTotal());
                // Somando o numero de atendimentos por bairro
                String idade= atendimentoPaciente.getNrAnos();
                atendimentoPerIdadeMap.put(idade, atendimentoPerIdadeMap.getOrDefault(idade, 0L) + atendimentoPaciente.getTotal());

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

            JSONArray atendimentosPorClinicaArray = new JSONArray();
            for (Map.Entry<String, Long> entry: atendimentoPerClinicaMap.entrySet()){
                JSONObject atendimentoPorClinica = new JSONObject();
                atendimentoPorClinica.put("clinica", entry.getKey());
                atendimentoPorClinica.put("total", entry.getValue());
                atendimentosPorClinicaArray.put(atendimentoPorClinica);
            }
            jsonResponse.put("atendimentoporclinica", atendimentosPorClinicaArray);

            JSONArray atendimentoPorMedicoArray = new JSONArray();
            for (Map.Entry<String, Long> entry: atendimentoPerMedicoMap.entrySet()){
                JSONObject atendimentoPorMedico = new JSONObject();
                atendimentoPorMedico.put("medico", entry.getKey());
                atendimentoPorMedico.put("total", entry.getValue());
                atendimentoPorMedicoArray.put(atendimentoPorMedico);
            }
            jsonResponse.put("atendimentopormedico", atendimentoPorMedicoArray);

            JSONArray atendimentoPorSetorArray = new JSONArray();
            for (Map.Entry<String, Long> entry: atendimentoPerSetorMap.entrySet()){
                JSONObject atendimentoPorSetor = new JSONObject();
                atendimentoPorSetor.put("setor", entry.getKey());
                atendimentoPorSetor.put("total", entry.getValue());
                atendimentoPorSetorArray.put(atendimentoPorSetor);
            }
            jsonResponse.put("atendimentoporsetor", atendimentoPorSetorArray);

            JSONArray atenidmentoPorIdadeArray = new JSONArray();
            for (Map.Entry<String, Long> entry: atendimentoPerIdadeMap.entrySet()){
                JSONObject atendimentoPorIdade = new JSONObject();
                atendimentoPorIdade.put("idade", entry.getKey());
                atendimentoPorIdade.put("total", entry.getValue());
                atenidmentoPorIdadeArray.put(atendimentoPorIdade);
            }
            jsonResponse.put("atendimentoporidade", atenidmentoPorIdadeArray);

        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonResponse.toString();
    }
}
