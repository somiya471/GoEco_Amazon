package com.example.goeco_amazon.responsemodels;

import com.example.goeco_amazon.models.ProductModel;

import java.util.ArrayList;

public class ProductResponse {
    private String message;
    private ArrayList<ProductModel> products;

    public ProductResponse(String message, ArrayList<ProductModel> products) {
        this.message = message;
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }
}
