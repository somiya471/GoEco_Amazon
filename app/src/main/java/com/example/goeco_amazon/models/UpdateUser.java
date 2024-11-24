package com.example.goeco_amazon.models;

public class UpdateUser {
    private String userid;
    private int ecoPoints;
    private double carbonSaved;
    private double caloriesBurned;

    public UpdateUser(String userid, int ecoPoints, double carbonSaved, double caloriesBurned) {
        this.userid = userid;
        this.ecoPoints = ecoPoints;
        this.carbonSaved = carbonSaved;
        this.caloriesBurned = caloriesBurned;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
