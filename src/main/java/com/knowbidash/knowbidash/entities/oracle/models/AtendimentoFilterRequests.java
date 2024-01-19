package com.knowbidash.knowbidash.entities.oracle.models;

import java.time.LocalDateTime;

public class AtendimentoFilterRequests {
    LocalDateTime startDate;
    LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
