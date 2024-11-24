package com.example.goeco_amazon.responsemodels;

public class SoloMetricsResponse {
    private String mode;
    private double carbon_saved;
    private double calories_burned;
    private int ecoPoints;
    private double distance;

    public SoloMetricsResponse(String mode, double carbon_saved, double calories_burned, int ecoPoints, double distance) {
        this.mode = mode;
        this.carbon_saved = carbon_saved;
        this.calories_burned = calories_burned;
        this.ecoPoints = ecoPoints;
        this.distance = distance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public double getCarbon_saved() {
        return carbon_saved;
    }

    public void setCarbon_saved(double carbon_saved) {
        this.carbon_saved = carbon_saved;
    }

    public double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(double calories_burned) {
        this.calories_burned = calories_burned;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
