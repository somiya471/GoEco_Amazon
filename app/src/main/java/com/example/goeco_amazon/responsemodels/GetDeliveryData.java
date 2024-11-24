package com.example.goeco_amazon.responsemodels;

import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.ProductModel;
import com.example.goeco_amazon.models.User;

public class GetDeliveryData {
    private String _id;
    private ProductModel product;
    private int quantity;
    private String delivery_date;
    private String time_slot;
    private String mode_of_transport;
    private ModelPickup delivery_address;
    private String recieve_time;
    private String delivery_status;
    private ModelUser user;
    private int ecoPoints;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public GetDeliveryData(String _id, ProductModel product, int quantity, String delivery_date, String time_slot, String mode_of_transport, ModelPickup delivery_address, String recieve_time, String delivery_status, ModelUser user, int ecoPoints, String createdAt, String updatedAt, int __v) {
        this._id = _id;
        this.product = product;
        this.quantity = quantity;
        this.delivery_date = delivery_date;
        this.time_slot = time_slot;
        this.mode_of_transport = mode_of_transport;
        this.delivery_address = delivery_address;
        this.recieve_time = recieve_time;
        this.delivery_status = delivery_status;
        this.user = user;
        this.ecoPoints = ecoPoints;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
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

    public ModelPickup getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(ModelPickup delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getRecieve_time() {
        return recieve_time;
    }

    public void setRecieve_time(String recieve_time) {
        this.recieve_time = recieve_time;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
