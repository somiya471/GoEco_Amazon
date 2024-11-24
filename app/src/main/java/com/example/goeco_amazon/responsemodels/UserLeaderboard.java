package com.example.goeco_amazon.responsemodels;

public class UserLeaderboard {
    private int position;
    private String username;
    private int ecoPoints;
    private double carbonSaved;
    private double caloriesBurned;

    public UserLeaderboard(int position, String username, int ecoPoints, double carbonSaved, double caloriesBurned) {
        this.position = position;
        this.username = username;
        this.ecoPoints = ecoPoints;
        this.carbonSaved = carbonSaved;
        this.caloriesBurned = caloriesBurned;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public double getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(double carbonSaved) {
        this.carbonSaved = carbonSaved;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}
