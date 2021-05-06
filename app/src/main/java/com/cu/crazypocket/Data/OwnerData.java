package com.cu.crazypocket.Data;

public class OwnerData {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name,upi,transection_id;
int Amount;

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getTransection_id() {
        return transection_id;
    }

    public void setTransection_id(String transection_id) {
        this.transection_id = transection_id;
    }
}
