package com.example.goeco_amazon.responsemodels;

import java.util.ArrayList;

public class GetDeliveryResponse {
    private String message;
    private ArrayList<GetDeliveryData> data;

    public GetDeliveryResponse(String message, ArrayList<GetDeliveryData> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<GetDeliveryData> getData() {
        return data;
    }

    public void setData(ArrayList<GetDeliveryData> data) {
        this.data = data;
    }
}
