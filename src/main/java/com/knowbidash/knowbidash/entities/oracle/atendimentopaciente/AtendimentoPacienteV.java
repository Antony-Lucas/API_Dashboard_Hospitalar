package com.knowbidash.knowbidash.entities.oracle.atendimentopaciente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @Column(name = "ds_convenio")
    private String dsConvenio;
    @Column(name = "ds_clinica")
    private String dsClinica;
    @Column(name = "ds_setor_atendimento")
    private String dsSetorAtendimento;
    @Column(name = "nr_anos")
    private String nrAnos;

    public AtendimentoPacienteV(){

    }
    public AtendimentoPacienteV(Integer nrAtendimento, String nmMedico, LocalDateTime dtEntrada, String dsConvenio, String dsClinica, String dsSetorAtendimento, String nrAnos) {
        this.nrAtendimento = nrAtendimento;
        this.nmMedico = nmMedico;
        this.dtEntrada = dtEntrada;
        this.dsConvenio = dsConvenio;
        this.dsClinica = dsClinica;
        this.dsSetorAtendimento = dsSetorAtendimento;
        this.nrAnos = nrAnos;
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
    public String getDsConvenio() {
        return dsConvenio;
    }
    public void setDsConvenio(String dsConvenio) {
        this.dsConvenio = dsConvenio;
    }
    public String getDsClinica() {
        return dsClinica;
    }
    public void setDsClinica(String dsClinica) {
        this.dsClinica = dsClinica;
    }
    public String getDsSetorAtendimento() {
        return dsSetorAtendimento;
    }
    public void setDsSetorAtendimento(String dsSetorAtendimento) {
        this.dsSetorAtendimento = dsSetorAtendimento;
    }

    public String getNrAnos() {
        return nrAnos;
    }

    public void setNrAnos(String nrAnos) {
        this.nrAnos = nrAnos;
    }
}
