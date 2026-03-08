package com.restaurant_booking.dto;

import java.time.LocalDateTime;

public class RecommendationRequestDto {

    private LocalDateTime startTime;
    private Integer durationHours;
    private int groupSize;

    private Boolean nearWindow;
    private Boolean quietArea;

    private String zone;

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

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public Boolean getNearWindow() {
        return nearWindow;
    }

    public void setNearWindow(Boolean nearWindow) {
        this.nearWindow = nearWindow;
    }

    public Boolean getQuietArea() {
        return quietArea;
    }

    public void setQuietArea(Boolean quietArea) {
        this.quietArea = quietArea;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}