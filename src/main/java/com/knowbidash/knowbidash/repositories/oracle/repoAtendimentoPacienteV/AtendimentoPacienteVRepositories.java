package com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV;

import com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtendimentoPacienteVRepositories extends JpaRepository<AtendimentoPacienteV, Long> {

    @Query("SELECT t FROM AtendimentoPacienteV t WHERE t.dtEntrada BETWEEN :startData AND :endData")
    List<AtendimentoPacienteV> findByDtEntradaBetween(@Param("startData") LocalDateTime startData, @Param("endData") LocalDateTime endData);

    @Query("SELECT NEW com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO(MONTH(t.dtEntrada), YEAR(t.dtEntrada), COUNT(t)) " +
            "FROM AtendimentoPacienteV t " +
            "WHERE (YEAR(t.dtEntrada) = :currentYear AND MONTH(t.dtEntrada) <= :currentMonth) " +
            "OR (YEAR(t.dtEntrada) < :currentYear) " +
            "GROUP BY MONTH(t.dtEntrada), YEAR(t.dtEntrada)")
    List<AtendimentoPacienteDTO> findTotalByMonthsInYear(@Param("currentYear") int year, @Param("currentMonth") int currentMonth);
}
