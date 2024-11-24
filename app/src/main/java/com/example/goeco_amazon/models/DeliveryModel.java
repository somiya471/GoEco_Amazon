package com.example.goeco_amazon.models;

public class DeliveryModel {
    private String product;
    private int quantity;
    private String delivery_date;
    private String time_slot;
    private String mode_of_transport;
    private String delivery_address;
    private String user;

    public DeliveryModel(String product, int quantity, String delivery_date, String time_slot, String mode_of_transport, String delivery_address, String user) {
        this.product = product;
        this.quantity = quantity;
        this.delivery_date = delivery_date;
        this.time_slot = time_slot;
        this.mode_of_transport = mode_of_transport;
        this.delivery_address = delivery_address;
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
