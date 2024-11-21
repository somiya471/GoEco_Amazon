package com.example.goeco_amazon.models;

public class MetricsModel {
    private double userDistance;
    private int userWeight;

    public MetricsModel(double userDistance, int userWeight) {
        this.userDistance = userDistance;
        this.userWeight = userWeight;
    }

    public double getUserDistance() {
        return userDistance;
    }

    public void setUserDistance(double userDistance) {
        this.userDistance = userDistance;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }
}
