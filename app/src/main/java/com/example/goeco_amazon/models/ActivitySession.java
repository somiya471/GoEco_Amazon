package com.example.goeco_amazon.models;

public class ActivitySession {
    private String activityType;
    private long startTime;
    private long endTime;
    private long duration;

    public ActivitySession(String activityType, long startTime) {
        this.activityType = activityType;
        this.startTime = startTime;
        this.endTime = 0;
        this.duration = 0;
    }

    // Getters and Setters
    public String getActivityType() {
        return activityType;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        this.duration = endTime - startTime;
    }

    public long getDuration() {
        return duration;
    }
}
