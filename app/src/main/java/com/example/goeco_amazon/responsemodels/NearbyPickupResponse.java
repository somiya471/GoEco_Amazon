package com.example.goeco_amazon.responsemodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.goeco_amazon.models.PickupPointModel;

import java.util.ArrayList;

public class NearbyPickupResponse {
    private ArrayList<PickupPointModel> pickupPointModels;

    public NearbyPickupResponse(ArrayList<PickupPointModel> pickupPointModels) {
        this.pickupPointModels = pickupPointModels;
    }

    public ArrayList<PickupPointModel> getPickupPointModels() {
        return pickupPointModels;
    }

    public void setPickupPointModels(ArrayList<PickupPointModel> pickupPointModels) {
        this.pickupPointModels = pickupPointModels;
    }
}
