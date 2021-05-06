package com.cu.crazypocket.Data;

public class Payment_data   {
    String status,mode,payment_type,merchant_name;
    Long timestamp;
    int amount_paid,actual_amount,coin_earned ,totalcoin ,coin_spent ,totalspent;

    public String getMerchant_name () {
        return merchant_name;
    }

    public void setMerchant_name ( String merchant_name ) {
        this.merchant_name = merchant_name;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public String getMode () {
        return mode;
    }

    public void setMode ( String mode ) {
        this.mode = mode;
    }

    public String getPayment_type () {
        return payment_type;
    }

    public void setPayment_type ( String payment_type ) {
        this.payment_type = payment_type;
    }

    public Long getTimestamp () {
        return timestamp;
    }

    public void setTimestamp ( Long timestamp ) {
        this.timestamp = timestamp;
    }

    public int getAmount_paid () {
        return amount_paid;
    }

    public void setAmount_paid ( int amount_paid ) {
        this.amount_paid = amount_paid;
    }

    public int getActual_amount () {
        return actual_amount;
    }

    public void setActual_amount ( int actual_amount ) {
        this.actual_amount = actual_amount;
    }

    public int getCoin_earned () {
        return coin_earned;
    }

    public void setCoin_earned ( int coin_earned ) {
        this.coin_earned = coin_earned;
    }

    public int getTotalcoin () {
        return totalcoin;
    }

    public void setTotalcoin ( int totalcoin ) {
        this.totalcoin = totalcoin;
    }

    public int getCoin_spent () {
        return coin_spent;
    }

    public void setCoin_spent ( int coin_spent ) {
        this.coin_spent = coin_spent;
    }

    public int getTotalspent () {
        return totalspent;
    }

    public void setTotalspent ( int totalspent ) {
        this.totalspent = totalspent;
    }

//    @Override
//    public int compareTo (Object o) {
//        Long time  =((((Payment_data) o).getTimestamp()) );
////        return  (int)(this.timestamp%10000) -(int)time;
//return time.compareTo(this.getTimestamp());
//    }
}
