package com.restaurant_booking.dto;

import java.time.LocalDateTime;

public class RecommendationRequestDto {

    private LocalDateTime startTime;
    private Integer durationHours; // default 2 in service
    private int groupSize;

    // Preferences (keep simple for now, can expand later)
    private Boolean nearWindow;
    private Boolean quietArea;

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
}