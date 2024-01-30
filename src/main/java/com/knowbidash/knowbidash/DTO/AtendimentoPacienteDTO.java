package com.knowbidash.knowbidash.DTO;

public class AtendimentoPacienteDTO {
    private int mes;
    private int year;
    private long total;
    private String dsConvenio;
    private String dsClinica;
    private String nmMedico;
    private String dsSetorAtendimento;
    private String nrAnos;

    public AtendimentoPacienteDTO(){}

    public AtendimentoPacienteDTO(int mes, int year, long total, String dsConvenio, String dsClinica, String nmMedico, String dsSetorAtendimento, String nrAnos){
        this.mes = mes;
        this.year = year;
        this.total = total;
        this.dsConvenio = dsConvenio;
        this.dsClinica = dsClinica;
        this.nmMedico = nmMedico;
        this.dsSetorAtendimento = dsSetorAtendimento;
        this.nrAnos = nrAnos;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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
    public String getNmMedico() {
        return nmMedico;
    }
    public void setNmMedico(String nmMedico) {
        this.nmMedico = nmMedico;
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
