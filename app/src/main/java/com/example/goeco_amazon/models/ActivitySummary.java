package com.example.goeco_amazon.models;

// ActivitySession.java

// ActivitySummary.java

public class ActivitySummary {
    private String activityType;
    private long totalDuration;
    private int occurrences;

    public ActivitySummary(String activityType) {
        this.activityType = activityType;
        this.totalDuration = 0;
        this.occurrences = 0;
    }

    public void addDuration(long duration) {
        this.totalDuration += duration;
        this.occurrences++;
    }

    // Getters
    public String getActivityType() {
        return activityType;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
