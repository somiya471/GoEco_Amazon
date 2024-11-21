package com.example.goeco_amazon.models;

public class ProductModel {
    private String _id;
    private String name;
    private int price;
    private String desc;
    private String image;
    private int __v;

    public ProductModel(String _id, String name, int price, String desc, String image, int __v) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.__v = __v;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
