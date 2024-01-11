package com.knowbidash.knowbidash.controller.oracle;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.services.oracle.AtendimentoPacienteVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AtendimentoPacienteVController {
    @Autowired
    private AtendimentoPacienteVService atendimentoPacienteVService;

    @GetMapping("/get/atendimentopaciente")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<AtendimentoPacienteV>> getAllAtendimentos(){
        List<AtendimentoPacienteV> list = atendimentoPacienteVService.findAllAtendimentos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get/filterdate")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<AtendimentoPacienteV>> getByDate(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startData,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endData)
    {
        List<AtendimentoPacienteV> result = atendimentoPacienteVService.findByDate(startData, endData);
        return ResponseEntity.ok(result);
    }
}
