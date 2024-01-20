package com.knowbidash.knowbidash.entities.oracle.atendimentopaciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FiltroData {
    private LocalDate startData;

    private LocalDate endData;

    public LocalDate getStartData() {
        return startData;
    }

    public void setStartData(LocalDate startData) {
        this.startData = startData;
    }

    public LocalDate getEndData() {
        return endData;
    }

    public void setEndData(LocalDate endData) {
        this.endData = endData;
    }
}
