package com.knowbidash.knowbidash.DTO;

public class AtendimentoPacienteDTO {
    private int mes;
    private int year;
    private long total;

    public AtendimentoPacienteDTO(int mes, int year, long total){
        this.mes = mes;
        this.year = year;
        this.total = total;
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
}
