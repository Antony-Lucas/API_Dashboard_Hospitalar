package com.knowbidash.knowbidash.services.oracle;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV.AtendimentoPacienteVRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendimentoPacienteVService {
    @Autowired
    private AtendimentoPacienteVRepositories atendimentoPacienteVRepositories;

    public List<AtendimentoPacienteV> findAllAtendimentos(){
        List<AtendimentoPacienteV> getAll = atendimentoPacienteVRepositories.findAll();
        return getAll;
    }
}
