package com.example.goeco_amazon.responsemodels;

public class ModelUser {
    private String _id;
    private String username;
    private String password;
    private int weight;
    private double latitude;
    private double longitude;
    private int ecoPoints;
    private double carbonSaved;
    private double caloriesBurned;
    private int __v;

    public ModelUser(String _id, String username, String password, int weight, double latitude, double longitude, int ecoPoints, double carbonSaved, double caloriesBurned, int __v) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ecoPoints = ecoPoints;
        this.carbonSaved = carbonSaved;
        this.caloriesBurned = caloriesBurned;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
