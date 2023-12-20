package com.knowbidash.knowbidash.entities.oracle.atendimentopaciente;

import jakarta.persistence.*;

@Entity
@Table(name = "atendimento_paciente_v")
public class AtendimentoPacienteV {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String nr_atendimento;
    private String nm_medico;
    private String dt_entrada;

    public String getNr_atendimento() {
        return nr_atendimento;
    }

    public void setNr_atendimento(String nr_atendimento) {
        this.nr_atendimento = nr_atendimento;
    }

    public String getNm_medico() {
        return nm_medico;
    }

    public void setNm_medico(String nm_medico) {
        this.nm_medico = nm_medico;
    }

    public String getDt_entrada() {
        return dt_entrada;
    }

    public void setDt_entrada(String dt_entrada) {
        this.dt_entrada = dt_entrada;
    }
}
