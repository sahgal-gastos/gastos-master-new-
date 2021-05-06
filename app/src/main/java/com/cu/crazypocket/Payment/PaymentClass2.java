package com.cu.crazypocket.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cu.crazypocket.Data.Trans_history;
import com.cu.crazypocket.FailureActivity;
import com.cu.crazypocket.PendingActivity;
import com.cu.crazypocket.R;
import com.cu.crazypocket.SuccessActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PaymentClass2 extends AppCompatActivity implements PaymentStatusListener {
    SharedPreferences sharedpreferences;
    static final String AMAZON_PAY = "in.amazon.mShop.android.shopping";
    static final String BHIM_UPI = "in.org.npci.upiapp";
    static final String GOOGLE_PAY = "com.google.android.apps.nbu.paisa.user";
    static final String PHONE_PE = "com.phonepe.app";
    static final String PAYTM = "net.one97.paytm";
    private static final String TAG ="Paymentclass2" ;
    TextView mShopName, mOwnerName, mLocation, mPhoneNumber, mUpiAddress;
    FirebaseDatabase database;
    DatabaseReference mref;
    FirebaseAuth mauth;
    Double MRPAmount= 0.0;
    CardView AfterPaymentCard;
    Animation fromtop;
    boolean checkbackpress=false;
    String[] data1;
    EditText billedamt,actualamt,savedamt, mrpprice, PayableAmount;
    boolean textchanged =false;
    final int UPI_PAYMENT =1;
    int t;
    String paymentmode ="";
    long timestamp=0;
    String tranref;
    Editable str;
    Intent intent;
    Double amt = 0.0 ;
    Double actualamt1 = 0.0;
    Double savedamt1 = 0.0;
void setamount(Editable s,Double MRPAmount,int flag){
    try{
        DecimalFormat df2 = new DecimalFormat("#.##");
        {
            String str = s.toString();
            if(!str.trim().equals(""))
            {
                 amt = Double.parseDouble(str) ;
                 actualamt1 = (amt-MRPAmount)*0.9+MRPAmount;
                 savedamt1 = (amt-MRPAmount)*0.1;
                if(MRPAmount>amt)
                    mrpprice.setError("Wrong Amount!");


                Log.e("amount log check",actualamt1+"  "+savedamt1);
                actualamt.setText(df2.format(actualamt1)+"");
                savedamt.setText(df2.format(savedamt1)+"");
            }
            else if(flag==1){
                actualamt.setText(MRPAmount.toString());
                savedamt.setText("0.0");
            }
            else{
                Log.e(TAG, "setamount: "+MRPAmount );
                if(Integer.parseInt(MRPAmount.toString())==0)
                    actualamt.setHint("0.0");


            savedamt.setText("0.0");
            }
        }
    }
    catch (Exception e){
        e.printStackTrace();
        actualamt.setText("0.0");
        savedamt.setText("0.0");
        if(flag == 0)
            billedamt.setError("Invalid Amount");
        else
            billedamt.setText("0.0");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_class2);
        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        intent = null;

        Bundle bundle = getIntent().getExtras();
        data1 = bundle.getStringArray("data");
        paymentmode = bundle.getString("paymentmode");

        sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        t = (int) (Math.random() * ((100000 - 1) + 1));

        AfterPaymentCard = findViewById(R.id.card__);
        timestamp = (Calendar.getInstance().getTimeInMillis());
        tranref=String.valueOf(timestamp)+t;
        fromtop = AnimationUtils.loadAnimation(this, R.anim.from_top);

        AfterPaymentCard.startAnimation(fromtop);

        billedamt = findViewById(R.id.billed_amount_text_id);
        mrpprice= findViewById(R.id.mrp_price);
        actualamt = findViewById(R.id.actualamount_txt);
        savedamt = findViewById(R.id.savedamount_txt);









        billedamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().trim().equals("")){

                    if(MRPAmount==0.0)
                        actualamt.setHint("0.0");
                }

            }





            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().trim().equals("")){

                if(MRPAmount==0.0)
                    actualamt.setHint("0.0");
            }
            }

            @Override
            public void afterTextChanged(Editable s) {
                str = s;
                setamount(s,MRPAmount,0);

            }
        });


        mrpprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(mrpprice.getText().toString().trim().equals("")){
                    MRPAmount=0.0;
                    setamount(str,MRPAmount,1);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mrpprice.getText().toString().trim().equals("")){
                    MRPAmount =Double.parseDouble(mrpprice.getText().toString().trim());
                    setamount(str,MRPAmount,1);
                }
                else{
                    MRPAmount=0.0;
                    setamount(str,MRPAmount,1);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(mrpprice.getText().toString().trim().equals("")){
                    MRPAmount=0.0;
                    setamount(str,MRPAmount,1);

                }

            }
        });


        mShopName = findViewById(R.id.textView15);
        mOwnerName = findViewById(R.id.textView23 );
        mLocation = findViewById(R.id.textView21 );
        mPhoneNumber = findViewById(R.id.textView34);
        mUpiAddress = findViewById(R.id.textView35);

        assert  data1 != null;
        String ShopName = data1[0];
        final String OwnerName = data1[1];
        String PhoneNumber = data1[2];
        String Location = data1[3];
        final String Upi =  data1[4];


        // setting up details
        mShopName.setText(ShopName);
        mOwnerName.setText(OwnerName);
        mPhoneNumber.setText(PhoneNumber);
        mLocation.setText(Location);
        mUpiAddress.setText(Upi);


        ((Button)findViewById(R.id.button5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MRPAmount<=amt){

                if(sharedpreferences.getBoolean("sub",false)){

                    try{

                        double NetAmt= 0.0;
                        double Billed= 0.0;

                        if(!billedamt.getText().toString().trim().equals("")){
                            Billed= Double.parseDouble(billedamt.getText().toString().trim());
                        }

                        NetAmt= Billed + MRPAmount;


                        if (!( NetAmt == 0.0 ) ){

                            //set min money
                            if(NetAmt < 1.0){
                                billedamt.setError("Invalid Amount");
                            }
                            else{

                                switch (paymentmode) {
                                    case "gpay":
//                            "7374070607@upi"  "8769652556@ybl"
                                        Log.e(TAG, "onClick: " + OwnerName);
                                        payusinggpay(OwnerName, Upi, "", actualamt.getText().toString().trim(), String.valueOf(timestamp) + t);

                                        break;
                                    case "paytm":

                                        payusingpaytm(OwnerName, Upi, "", actualamt.getText().toString().trim(), String.valueOf(timestamp) + t);

                                        break;
                                    case "bhim":
                                        payusingbhim(OwnerName, Upi, "", actualamt.getText().toString().trim(), String.valueOf(timestamp) + t);

                                        break;
                                    case "phonepe":
                                        payusingphonepay(OwnerName, Upi, "", actualamt.getText().toString().trim(), String.valueOf(timestamp) + t, data1[6]);
                                        break;
                                }



                            }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        billedamt.setError("Invalid Amount");
                    }

                }else{
                    Log.e(TAG, "onClick: "+sharedpreferences.getBoolean("sub",false) );
                    try {


                        View contextView = findViewById(R.id.paymentlayout);

                        Snackbar snackbar = Snackbar.make(contextView, "Your Account is currently inactive. please first active your account from profile section.", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();

                    }
                    catch (IllegalArgumentException e)
                    {

                    }

//                    Toast.makeText(PaymentClass2.this,"You haven't to Subscribed to our Services",Toast.LENGTH_SHORT).show();
                }

                }
                else{

                    //TODO: Show toast
//                    Toast.makeText(PaymentClass2.this,"Wrong Amount",Toast.LENGTH_LONG).show();
                }
             }
        }
        );
    }


    // PAYMENT OPTIONS

    // google pay paying method
    void payusinggpay(String name, String upiId, String note, String amount, String tranref){

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        intent = new Intent(Intent.ACTION_VIEW, uri);

        intent.setPackage(GOOGLE_PAY);

        if(null != intent.resolveActivity(getPackageManager())){
            paymentinitail(tranref, data1[5]);
            startActivityForResult(intent, UPI_PAYMENT);
        }else{
            Toast.makeText(PaymentClass2.this, "Google Pay Not Installed On Your Device", Toast.LENGTH_SHORT).show();
        }
    }



    // Bhim paying method

    void payusingbhim(String name,String upiId, String note, String amount,String tranref){

        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();



        intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(BHIM_UPI);

        if(intent.resolveActivity(getPackageManager()) != null){
            paymentinitail(tranref, data1[5]);
            startActivityForResult(intent,1);
        }else{
            Toast.makeText(PaymentClass2.this, "Install BHIM on Your Device", Toast.LENGTH_SHORT).show();
        }
    }



    // Pay using PayTm

    void payusingpaytm(String name,String upiId, String note, String amount,String tranref) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();




        intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(PAYTM);


        if (null != intent.resolveActivity(getPackageManager())) {
            paymentinitail(tranref, data1[5]);
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(PaymentClass2.this, "Install PayTm on your device", Toast.LENGTH_SHORT).show();
        }
    }


    // Pay using PhonePay

    void payusingphonepay(String name, String upiId, String note, String amount, String tranref,String tranid){
        Uri uri;
        if(tranid.equals("0"))
          uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
        else
          uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                  .appendQueryParameter("tr", tranref)
                  .appendQueryParameter("tn", tranid)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();




        intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(PHONE_PE);
        if (null != intent.resolveActivity(getPackageManager())) {
            paymentinitail(tranref, data1[5]);
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(PaymentClass2.this, "Install PhonePe on your device", Toast.LENGTH_SHORT).show();
        }

    }


    // Pay using amazon pay




    void payUsingUpi( String name,String upiId, String note, String amount,String tranref) {

        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", "ms2004151341148690000798")
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", "1.00")
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        paymentinitail(tranref, data1[5]);
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(PaymentClass2.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        try {
            Log.e("main ", "response " + resultCode+"  "+data.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            paymentcancel( );
        }

        try{ Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            switch (requestCode) {
                case UPI_PAYMENT:
                    if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                        if (data != null) {
                            String trxt = data.getStringExtra("response");
                            Log.e("UPI", "onActivityResult: " + trxt);
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add(trxt);
                            upiPaymentDataOperation(dataList);
                        } else {
                            Log.e("UPI", "onActivityResult: " + " sadfds Return data is null");
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add("nothing");
                            upiPaymentDataOperation(dataList);
                        }
                    } else {
                        //when user simply back without payment
                        Log.e("UPI", "onActivityResult: " );
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                    break;
            }}catch(Exception e){
            e.printStackTrace();

        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable( PaymentClass2.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText( this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
                paymentsuccess();

            } else if (status.equals("pending")) {
                //Code to handle successful transaction here.
                Toast.makeText( this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment pending: "+approvalRefNo);
                paymentpending();

            }
            else if (status.equals("fail")) {
                //Code to handle successful transaction here.
                Toast.makeText( this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment pending: "+approvalRefNo);
                paymentfail();


            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText( this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
                    paymentcancel();
            }
            else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
                paymentfail();
            }
        } else {

            Log.e("UPI", "Internet issue: ");
            paymentcancel();
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(PaymentClass2 context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }




    void paymentinitail(String tranref,String merchantuid){
        Trans_history trans = new Trans_history();
        trans.setActual_amount(Double.valueOf(billedamt.getText().toString().trim()));
        trans.setAmount_paid(Double.valueOf(actualamt.getText().toString().trim()));

        trans.setTran_ref(tranref);
        trans.setMerchant_uid(merchantuid);
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Processing");
        trans.setMode(paymentmode);
        trans.setPayment_type("merchant");
        trans.setTimestamp(timestamp);

        trans.setMerchant_name(data1[0]);
        trans.setUser_name(sharedpreferences.getString("username",""));
        Log.e(TAG, "paymentinitail: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);

        mref.push();
        mref = database.getReference("Transaction_History_merchant/"+merchantuid.trim()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
    }


    void paymentcancel( ){
        Log.e(TAG, "paymentcancel: " );
        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.removeValue();
        mref.push();
        mref = database.getReference("Transaction_History_merchant/"+data1[5].trim()+"/"+tranref);
        mref.removeValue();
        mref.push();

    }

    void paymentsuccess()
    {
        Trans_history trans = new Trans_history();
        trans.setActual_amount(Double.valueOf(billedamt.getText().toString().trim()));
        trans.setAmount_paid(Double.valueOf(actualamt.getText().toString().trim()));

        trans.setTran_ref(tranref);
        trans.setMerchant_uid(data1[5]);
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Success");
        trans.setMode(paymentmode);
        trans.setPayment_type("merchant");
        trans.setTimestamp(timestamp);
        trans.setCoin_earned( (Double.valueOf(actualamt.getText().toString().trim())).intValue());
        trans.setMerchant_name(data1[0]);
        trans.setUser_name(sharedpreferences.getString("username",""));

         mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();

        mref = database.getReference("Transaction_History_merchant/"+data1[5].trim()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
        Log.e(TAG, "paymentsuccess: " );
         Intent intent = new Intent(PaymentClass2.this, SuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("data", data1);
        intent.putExtra("data1", new String[]{(Double.valueOf(actualamt.getText().toString().trim())).toString()
                ,(Double.valueOf(billedamt.getText().toString().trim())).toString(),String.valueOf(timestamp)});
        startActivity(intent);
    }
    void paymentfail()
    {
        Trans_history trans = new Trans_history();
        trans.setActual_amount(Double.valueOf(billedamt.getText().toString().trim()));
        trans.setAmount_paid(Double.valueOf(actualamt.getText().toString().trim()));

        trans.setTran_ref(tranref);
        trans.setMerchant_uid(data1[5]);
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Failed");
        trans.setMode(paymentmode);
        trans.setPayment_type("merchant");
        trans.setTimestamp(timestamp);
         trans.setMerchant_name(data1[0]);
        trans.setUser_name(sharedpreferences.getString("username",""));

        Log.e(TAG, "paymentinitail: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();

        mref = database.getReference("Transaction_History_merchant/"+data1[5].trim()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
        Log.e(TAG, "paymentfail: ");
        Intent intent = new Intent(PaymentClass2.this, FailureActivity.class);
        intent.putExtra("data", data1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void paymentpending()
    {
        Trans_history trans = new Trans_history();
        trans.setActual_amount(Double.valueOf(billedamt.getText().toString().trim()));
        trans.setAmount_paid(Double.valueOf(actualamt.getText().toString().trim()));

        trans.setTran_ref(tranref);
        trans.setMerchant_uid(data1[5]);
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Pending");
        trans.setMode(paymentmode);
        trans.setPayment_type("merchant");
        trans.setTimestamp(timestamp);
         trans.setMerchant_name(data1[0]);
        trans.setUser_name(sharedpreferences.getString("username",""));

        Log.e(TAG, "paymentpending: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();

        mref = database.getReference("Transaction_History_merchant/"+data1[5].trim()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
        Log.e(TAG, "paymentpending: " );
        Intent intent = new Intent(PaymentClass2.this, PendingActivity.class);
        intent.putExtra("data", data1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

//
//    @Override
//    public void onBackPressed() {
//        checkbackpress=true;
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onDestroy() {
//
//        if(checkbackpress==false){
//            paymentpending();
//            Log.e(TAG, "onDestroy: ");
//
//        }
//        super.onDestroy();
//    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Log.e(TAG, "onTransactionCompleted: "+ transactionDetails.toString() );
    }

    @Override
    public void onTransactionSuccess() {
        Log.e(TAG, "onTransactionSuccess: " );
    }

    @Override
    public void onTransactionSubmitted() {
        Log.e(TAG, "onTransactionSubmitted: " );
    }

    @Override
    public void onTransactionFailed() {
        Log.e(TAG, "onTransactionFailed: ");
    }

    @Override
    public void onTransactionCancelled() {
        Log.e(TAG, "onTransactionCancelled: " );
    }

    @Override
    public void onAppNotFound() {
        Log.e(TAG, "onAppNotFound: " );
    }

}
