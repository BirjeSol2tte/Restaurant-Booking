package com.restaurant_booking.dto;

public class TableAvailabilityDto {

    private Long tableId;
    private boolean available;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}