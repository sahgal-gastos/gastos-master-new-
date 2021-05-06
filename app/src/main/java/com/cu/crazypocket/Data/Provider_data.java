package com.cu.crazypocket.Data;

public class Provider_data {

    private String ownername,shopname,phoneno,email,address,shoppic,  Location,merchant_uid;
    private Provider_data_data data;

    public String getMerchant_uid() {
        return merchant_uid;
    }

    public void setMerchant_uid(String merchant_uid) {
        this.merchant_uid = merchant_uid;
    }

    public Provider_data_data getData () {
        return data;
    }

    public void setData ( Provider_data_data data ) {
        this.data = data;
    }

    public String getOwnername () {
        return ownername;
    }

    public void setOwnername ( String ownername ) {
        this.ownername = ownername;
    }

    public String getShopname () {
        return shopname;
    }

    public void setShopname ( String shopname ) {
        this.shopname = shopname;
    }

    public String getPhoneno () {
        return phoneno;
    }

    public void setPhoneno ( String phoneno ) {
        this.phoneno = phoneno;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( String address ) {
        this.address = address;
    }

    public String getShoppic () {
        return shoppic;
    }

    public void setShoppic ( String shoppic ) {
        this.shoppic = shoppic;
    }


    public String getLocation () {
        return Location;
    }

    public void setLocation ( String location ) {
        Location = location;
    }
}
