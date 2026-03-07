package com.restaurant_booking.dto;

import java.time.LocalDateTime;

public class ReservationRequestDto {

    private Long tableId;
    private LocalDateTime startTime;

    // If not provided, we’ll default to 2 hours in the service
    private Integer durationHours;

    private boolean includesDishOfTheDay;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isIncludesDishOfTheDay() {
        return includesDishOfTheDay;
    }

    public void setIncludesDishOfTheDay(boolean includesDishOfTheDay) {
        this.includesDishOfTheDay = includesDishOfTheDay;
    }
}