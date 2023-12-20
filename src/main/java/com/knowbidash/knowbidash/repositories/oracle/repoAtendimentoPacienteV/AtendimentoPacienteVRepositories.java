package com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoPacienteVRepositories extends JpaRepository<AtendimentoPacienteV, Long> {
}
