package com.restaurant_booking.dto;

import java.time.LocalDateTime;

public class ReservationResponseDto {

    private String reservationCode;
    private Long tableId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean includesDishOfTheDay;

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isIncludesDishOfTheDay() {
        return includesDishOfTheDay;
    }

    public void setIncludesDishOfTheDay(boolean includesDishOfTheDay) {
        this.includesDishOfTheDay = includesDishOfTheDay;
    }
}