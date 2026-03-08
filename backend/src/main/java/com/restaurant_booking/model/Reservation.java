package com.restaurant_booking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tableId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String reservationCode;

    private boolean includesDishOfTheDay;

    private String dishName;

    public Long getId() {
        return id;
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

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
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