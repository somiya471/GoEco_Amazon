package com.example.goeco_amazon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PickupPointModel implements Parcelable {

    private String _id;
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private int __v;
    private double distance;

    // Constructor
    public PickupPointModel(String _id, String name, double latitude, double longitude, String address, int __v, double distance) {
        this._id = _id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.__v = __v;
        this.distance = distance;
    }

    // Parcelable Constructor
    protected PickupPointModel(Parcel in) {
        _id = in.readString();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        address = in.readString();
        __v = in.readInt();
        distance = in.readDouble();
    }

    // Parcelable Creator
    public static final Creator<com.example.goeco_amazon.models.PickupPointModel> CREATOR = new Creator<com.example.goeco_amazon.models.PickupPointModel>() {
        @Override
        public com.example.goeco_amazon.models.PickupPointModel createFromParcel(Parcel in) {
            return new com.example.goeco_amazon.models.PickupPointModel(in);
        }

        @Override
        public com.example.goeco_amazon.models.PickupPointModel[] newArray(int size) {
            return new com.example.goeco_amazon.models.PickupPointModel[size];
        }
    };

    // Getter and Setter methods
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Parcelable Implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(address);
        dest.writeInt(__v);
        dest.writeDouble(distance);
    }
}
