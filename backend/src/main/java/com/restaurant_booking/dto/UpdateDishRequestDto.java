package com.restaurant_booking.dto;

public class UpdateDishRequestDto {

    private boolean includesDishOfTheDay;

    public boolean isIncludesDishOfTheDay() {
        return includesDishOfTheDay;
    }

    public void setIncludesDishOfTheDay(boolean includesDishOfTheDay) {
        this.includesDishOfTheDay = includesDishOfTheDay;
    }
}