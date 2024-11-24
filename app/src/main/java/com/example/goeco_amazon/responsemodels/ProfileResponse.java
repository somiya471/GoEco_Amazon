package com.example.goeco_amazon.responsemodels;

import com.example.goeco_amazon.models.User;

import java.util.ArrayList;

public class ProfileResponse {
    private String message;
    private ArrayList<User> data;

    public ProfileResponse(String message, ArrayList<User> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
