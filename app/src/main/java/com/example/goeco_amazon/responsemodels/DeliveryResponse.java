package com.example.goeco_amazon.responsemodels;

public class DeliveryResponse {
    private String message;
    private DeliveryData data;

    public DeliveryResponse(String message, DeliveryData data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeliveryData getData() {
        return data;
    }

    public void setData(DeliveryData data) {
        this.data = data;
    }
}
