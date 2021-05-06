package com.cu.crazypocket.Payment;

import android.media.Image;

public class Payment_options {

    String paymentoption_name;
    Image image;

    public Payment_options(String paymentoption_name) {
        this.paymentoption_name = paymentoption_name;
        //this.image = image;
    }

    public String getPaymentoption_name() {
        return paymentoption_name;
    }

    public void setPaymentoption_name(String paymentoption_name) {
        this.paymentoption_name = paymentoption_name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
