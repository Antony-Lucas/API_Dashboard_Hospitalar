package com.knowbidash.knowbidash.entities.oracle.atendimentopaciente;

import java.time.LocalDateTime;

public class FiltroData {
    private LocalDateTime startData;

    private LocalDateTime endData;

    public LocalDateTime getStartData() {
        return startData;
    }

    public void setStartData(LocalDateTime startData) {
        this.startData = startData;
    }

    public LocalDateTime getEndData() {
        return endData;
    }

    public void setEndData(LocalDateTime endData) {
        this.endData = endData;
    }
}
