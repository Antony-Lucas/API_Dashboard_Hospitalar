package com.knowbidash.knowbidash.entities.oracle.atendimentopaciente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimento_paciente_v")
public class AtendimentoPacienteV {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nr_atendimento")
    private Integer nrAtendimento;
    @Column(name = "nm_medico")
    private String nmMedico;
    @Column(name = "dt_entrada")
    private LocalDateTime dtEntrada;

    public AtendimentoPacienteV(){

    }
    public AtendimentoPacienteV(Integer nrAtendimento, String nmMedico, LocalDateTime dtEntrada) {
        this.nrAtendimento = nrAtendimento;
        this.nmMedico = nmMedico;
        this.dtEntrada = dtEntrada;
    }

    public Integer getNrAtendimento() {
        return nrAtendimento;
    }

    public void setNrAtendimento(Integer nrAtendimento) {
        this.nrAtendimento = nrAtendimento;
    }

    public String getNmMedico() {
        return nmMedico;
    }

    public void setNmMedico(String nmMedico) {
        this.nmMedico = nmMedico;
    }

    public LocalDateTime getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(LocalDateTime dtEntrada) {
        this.dtEntrada = dtEntrada;
    }
}
