package com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV;

import com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO;
import com.knowbidash.knowbidash.entities.oracle.atendimentopaciente.AtendimentoPacienteV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtendimentoPacienteVRepositories extends JpaRepository<AtendimentoPacienteV, Long> {
    List<AtendimentoPacienteV> findByDtEntradaBetween(LocalDateTime startData, LocalDateTime endData);
    @Query("SELECT NEW com.knowbidash.knowbidash.DTO.AtendimentoPacienteDTO(MONTH(t.dtEntrada), YEAR(t.dtEntrada), COUNT(t), t.dsConvenio, t.dsClinica, t.nmMedico, t.dsSetorAtendimento, t.nrAnos) " +
            "FROM AtendimentoPacienteV t " +
            "WHERE t.dtEntrada >= :startDate AND t.dtEntrada <= :endDate " +
            "GROUP BY MONTH(t.dtEntrada), YEAR(t.dtEntrada), t.dsConvenio, t.dsClinica, t.nmMedico, t.dsSetorAtendimento, t.nrAnos")
    List<AtendimentoPacienteDTO> findTotalAtendimentosInPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
