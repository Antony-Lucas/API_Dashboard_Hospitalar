package com.knowbidash.knowbidash.DTO;

public class AtendimentoPacienteDTO {
    private int mes;
    private int year;
    private long total;
    private String dsConvenio;

    public AtendimentoPacienteDTO(){}

    public AtendimentoPacienteDTO(int mes, int year, long total, String dsConvenio){
        this.mes = mes;
        this.year = year;
        this.total = total;
        this.dsConvenio = dsConvenio;
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
}
