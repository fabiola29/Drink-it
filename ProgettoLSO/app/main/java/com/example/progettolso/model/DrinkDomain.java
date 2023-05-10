package com.example.progettolso.model;

import java.io.Serializable;

public class DrinkDomain implements Serializable {

    private String title;
    private String pic;
    private String descriptor;
    private Double fee;
    private int numberInCart;


    public DrinkDomain(String title, String descriptor, Double fee, String pic) {
        this.title = title;
        this.descriptor = descriptor;
        this.fee = fee;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
