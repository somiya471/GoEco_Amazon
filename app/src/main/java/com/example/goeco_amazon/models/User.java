package com.example.goeco_amazon.models;

public class User {
    private String username;
    private String password;
    private int weight;
    private double latitude;
    private double longitude;
    private String _id;
    private int __v;

    public User(String username, String password, int weight, double latitude, double longitude, String _id, int __v) {
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.latitude = latitude;
        this.longitude = longitude;
        this._id = _id;
        this.__v = __v;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}