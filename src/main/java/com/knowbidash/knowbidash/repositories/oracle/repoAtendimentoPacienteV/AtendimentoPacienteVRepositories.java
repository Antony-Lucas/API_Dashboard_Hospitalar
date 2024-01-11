package com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV;

import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtendimentoPacienteVRepositories extends JpaRepository<AtendimentoPacienteV, Long> {

    @Query("SELECT new AtendimentoPacienteV(t.nrAtendimento, t.nmMedico, t.dtEntrada) FROM AtendimentoPacienteV t")
    List<AtendimentoPacienteV> listAtendimentosData();

    @Query("SELECT t FROM AtendimentoPacienteV t WHERE t.dtEntrada BETWEEN :startData AND :endData")
    List<AtendimentoPacienteV> findByDtEntradaBetween(@Param("startData") LocalDateTime startData, @Param("endData") LocalDateTime endData);
}
