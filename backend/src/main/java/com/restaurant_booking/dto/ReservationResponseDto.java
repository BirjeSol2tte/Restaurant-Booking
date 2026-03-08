package com.restaurant_booking.dto;

import java.time.LocalDateTime;

public class ReservationResponseDto {

    private String reservationCode;
    private Long tableId;
    private String tableLabel;
    private String zone;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean includesDishOfTheDay;
    private String dishName;

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

    public String getTableLabel() {
        return tableLabel;
    }

    public void setTableLabel(String tableLabel) {
        this.tableLabel = tableLabel;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}