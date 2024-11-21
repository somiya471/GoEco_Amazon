package com.example.goeco_amazon.responsemodels;

import com.example.goeco_amazon.models.User;

public class RegisterResponse {
    private String message;
    private User user;

    public RegisterResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
