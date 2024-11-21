package com.example.goeco_amazon.responsemodels;

public class MetricsData {
    private String mode;
    private double carbon_saved;
    private double calories_burned;
    private int ecoPoints;

    public MetricsData(String mode, double carbon_saved, double calories_burned, int ecoPoints) {
        this.mode = mode;
        this.carbon_saved = carbon_saved;
        this.calories_burned = calories_burned;
        this.ecoPoints = ecoPoints;
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
}
