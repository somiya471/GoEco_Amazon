package com.example.goeco_amazon.responsemodels;

import java.util.ArrayList;

public class MetricsResponse {
    private ArrayList<MetricsData> data;

    public MetricsResponse(ArrayList<MetricsData> data) {
        this.data = data;
    }

    public ArrayList<MetricsData> getData() {
        return data;
    }

    public void setData(ArrayList<MetricsData> data) {
        this.data = data;
    }
}
