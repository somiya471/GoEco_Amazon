package com.example.goeco_amazon.models;

public class SoloMetricsData {
    private double lat;
    private double lon;
    private double lat1;
    private double lon1;
    private int userWeight;
    private String mode_of_transport;

    public SoloMetricsData(double lat, double lon, double lat1, double lon1, int userWeight, String mode_of_transport) {
        this.lat = lat;
        this.lon = lon;
        this.lat1 = lat1;
        this.lon1 = lon1;
        this.userWeight = userWeight;
        this.mode_of_transport = mode_of_transport;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLon1() {
        return lon1;
    }

    public void setLon1(double lon1) {
        this.lon1 = lon1;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }
}
