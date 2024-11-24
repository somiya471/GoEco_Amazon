package com.example.goeco_amazon.models;

public class UpdateDelivery {
    private String delivery_status;
    private String recieve_time;
    private int ecoPoints;
    private String mode_of_transport;

    public UpdateDelivery(String delivery_status, String recieve_time, int ecoPoints, String mode_of_transport) {
        this.delivery_status = delivery_status;
        this.recieve_time = recieve_time;
        this.ecoPoints = ecoPoints;
        this.mode_of_transport = mode_of_transport;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getRecieve_time() {
        return recieve_time;
    }

    public void setRecieve_time(String recieve_time) {
        this.recieve_time = recieve_time;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }
}
