package com.cu.crazypocket.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.cu.crazypocket.Data.Sub_data;
import com.cu.crazypocket.FailureActivity;
import com.cu.crazypocket.PendingActivity;
import com.cu.crazypocket.R;
import com.cu.crazypocket.SuccessActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Subsciption_Payment2 extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    static final String AMAZON_PAY = "in.amazon.mShop.android.shopping";
    static final String BHIM_UPI = "in.org.npci.upiapp";
    static final String GOOGLE_PAY = "com.google.android.apps.nbu.paisa.user";
    static final String PHONE_PE = "com.phonepe.app";
    static final String PAYTM = "net.one97.paytm";
    private static final String TAG = "Paymentclass2";
    final int UPI_PAYMENT = 1;
    FirebaseDatabase database;
    DatabaseReference mref;
    Animation fromtop;
    String tranref;
    CardView AfterPaymentCard;
    FirebaseAuth mauth;
    TextView mAmountPaid, mCoinsStatus;
    Button mUseCoinButton, mProceedButton;

    private int coins = 0;
    private long timestamp = 0;
    private static String paymentmode;
    private int t;
    String[] data, data1;
    Bundle bundle;
    Intent intent;
    int Amount;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsciption__payment2);
        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        timestamp = (Calendar.getInstance().getTimeInMillis());
        t = (int) (Math.random() * ((100000 - 1) + 1));
        tranref = String.valueOf(timestamp) + t;
        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();
            paymentmode = bundle.getString("paymentmode");
            data = (bundle.getStringArray("data"));
            Amount = Integer.parseInt(data[3]);

            Log.e(TAG, "onCreate: " + data[1]);
        }

        AfterPaymentCard = findViewById(R.id.card__);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        AfterPaymentCard.startAnimation(fromtop);


        // amount paid calculation
        mAmountPaid = findViewById(R.id.textView54);
        mUseCoinButton = findViewById(R.id.button3);
        mCoinsStatus = findViewById(R.id.textView51);

        mCoinsStatus.setText(data[0]);

        mUseCoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coins = Integer.parseInt(mCoinsStatus.getText().toString().trim());

                if (coins >= 250) {

                    int coinsremaining = coins - 250;
                    clicked = true;
                    mCoinsStatus.setText(String.valueOf(coinsremaining));
                    mAmountPaid.setText((Amount - 50) + "");
                }

            }
        });


        // Proceed Button

        mProceedButton = findViewById(R.id.button5);

        mProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (paymentmode.equals("gpay")) {
                    if(clicked)
                    payusinggpay(data[4], data[1], " ", String.valueOf(Amount - 50), String.valueOf(timestamp) + t);
                    else
                        payusinggpay(data[4], data[1], " ", String.valueOf(Amount), String.valueOf(timestamp) + t);

                } else if (paymentmode.equals("paytm")) {
                    if(clicked)
                        payusingpaytm(data[4], data[1], " ", String.valueOf(Amount - 50), String.valueOf(timestamp) + t);
                    else
                        payusingpaytm(data[4], data[1], " ", String.valueOf(Amount), String.valueOf(timestamp) + t);


                } else if (paymentmode.equals("bhim")) {
                    if(clicked)
                        payusingbhim(data[4], data[1], " ", String.valueOf(Amount - 50), String.valueOf(timestamp) + t);
                    else
                        payusingbhim(data[4], data[1], " ", String.valueOf(Amount), String.valueOf(timestamp) + t);


                } else if (paymentmode.equals("phonepe")) {
                    if(clicked)
                        payusingphonepay(data[4], data[1], " ", String.valueOf(Amount - 50), String.valueOf(timestamp) + t,data[5]);
                    else
                        payusingphonepay(data[4], data[1], " ", String.valueOf(Amount), String.valueOf(timestamp) + t,data[5]);

                    }


            }
        });


    }


    // PAYMENT OPTIONS

    // google pay paying method
    void payusinggpay(String name, String upiId, String note, String amount, String tranref) {


        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
               .appendQueryParameter("mc","")  //undo
               .appendQueryParameter("tr", tranref)
                .appendQueryParameter("tn", "25584584") //undo
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        intent = new Intent(Intent.ACTION_VIEW, uri);

        intent.setPackage(GOOGLE_PAY);

        if (null != intent.resolveActivity(getPackageManager())) {
            paymentinitail(tranref);

            startActivityForResult(intent, UPI_PAYMENT);
            Toast.makeText(Subsciption_Payment2.this,data[1],Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Subsciption_Payment2.this, "Google Pay Not Installed On Your Device", Toast.LENGTH_SHORT).show();
        }
    }


    // Bhim paying method

    void payusingbhim(String name, String upiId, String note, String amount, String tranref) {

        Log.e("main ", "name " + name + "--up--" + upiId + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu", "INR")
                .build();


        intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(BHIM_UPI);

        if (null != intent.resolveActivity(getPackageManager())) {
            paymentinitail(tranref);

            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(Subsciption_Payment2.this, "Install BHIM on Your Device", Toast.LENGTH_SHORT).show();
        }
    }


    // Pay using PayTm

    void payusingpaytm(String name, String upiId, String note, String amount, String tranref) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();



        intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(PAYTM);
        if (null != intent.resolveActivity(getPackageManager())) //changed heree
        {
            paymentinitail(tranref);

            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(Subsciption_Payment2.this, "Install PayTm on your device", Toast.LENGTH_SHORT).show();
        }
    }


    // Pay using PhonePay

    void payusingphonepay(String name, String upiId, String note, String amount, String tranref,String tranid) {

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
            paymentinitail(tranref);

            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(Subsciption_Payment2.this, "Install PhonePe on your device", Toast.LENGTH_SHORT).show();
        }

    }



    void payUsingUpi( String name,String upiId, String note, String amount,String tranref) {

        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", "ms2004151341148690000798")
                .appendQueryParameter("tr", tranref)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            paymentinitail(tranref );

            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Subsciption_Payment2.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
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
        if (isConnectionAvailable( Subsciption_Payment2.this)) {
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

    public static boolean isConnectionAvailable(Subsciption_Payment2 context) {
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




    void paymentinitail(String tranref){
        Sub_data trans = new Sub_data();

        trans.setStartime(0);
        trans.setEndtime(0);
        if(!clicked) {
            trans.setActual_amount(Double.valueOf(Amount));
            trans.setAmount_paid(Double.valueOf(Amount));
        }
        else{
            trans.setActual_amount(Double.valueOf(Amount-50));
            trans.setAmount_paid(Double.valueOf(Amount-50));
        }

        trans.setTran_ref(tranref);
        trans.setMerchant_uid("");
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Processing");
        trans.setMode(paymentmode);
        trans.setPayment_type("Subscription");
        trans.setTimestamp(timestamp);

        trans.setMerchant_name(data[4]);
        trans.setUser_name(sharedpreferences.getString("username",""));
        Log.e(TAG, "paymentinitail: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);

        mref.push();
        mref = database.getReference("Transaction_subsciption/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
    }


    void paymentcancel( ){
        Log.e(TAG, "paymentcancel: " );
        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.removeValue();
        mref.push();
        mref = database.getReference("Transaction_subsciption/"+mauth.getUid()+"/"+tranref);
        mref.removeValue();
        mref.push();

    }

    void paymentsuccess()
    {
        Sub_data trans = new Sub_data();
        if(!clicked) {
            data1=new String[] {"Gastos",String.valueOf(Amount),String.valueOf(timestamp)};

            trans.setActual_amount(Double.valueOf(Amount));
            trans.setAmount_paid(Double.valueOf(Amount));
        }
        else{
            data1=new String[] {"Gastos",String.valueOf(Amount-50),String.valueOf(timestamp)};

            trans.setCoin_spent(250);
            trans.setActual_amount(Double.valueOf(Amount-50));
            trans.setAmount_paid(Double.valueOf(Amount-50));
        }
        Calendar cal = Calendar.getInstance();
        long timestamp1 = (cal.getTimeInMillis());
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
        long timestamp2 = (cal.getTimeInMillis());
        trans.setStartime(timestamp1);
        trans.setEndtime(timestamp2);
        trans.setTran_ref(tranref);
         trans.setUser_uid(mauth.getUid());
        trans.setStatus("Success");
        trans.setMode(paymentmode);
        trans.setPayment_type("Subscription");
        trans.setTimestamp(timestamp);
        trans.setCoin_earned( 0);

        trans.setMerchant_name(data[4]);
        trans.setUser_name(sharedpreferences.getString("username",""));

        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);

        mref.push();
        mref = database.getReference("Transaction_subsciption/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();


        mref = database.getReference("/ref_code_History/"+sharedpreferences.getString("Ref","")+"/"+mauth.getUid()  );
        mref.setValue(true);
        mref.push();

        Log.e(TAG, "paymentsuccess: " );
        Intent intent = new Intent(Subsciption_Payment2.this, SuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("data", data1);
        startActivity(intent);
    }
    void paymentfail()
    {
        Sub_data trans = new Sub_data();
        trans.setStartime(0);
        trans.setEndtime(0);
        if(!clicked) {
            trans.setActual_amount(Double.valueOf(Amount));
            trans.setAmount_paid(Double.valueOf(Amount));
        }
        else{
            trans.setActual_amount(Double.valueOf(Amount-50));
            trans.setAmount_paid(Double.valueOf(Amount-50));
        }
        trans.setCoin_earned( 0);
        trans.setCoin_spent(0);
        trans.setTran_ref(tranref);
         trans.setUser_uid(mauth.getUid());
        trans.setStatus("Failed");
        trans.setMode(paymentmode);
        trans.setPayment_type("Subscription");
        trans.setTimestamp(timestamp);
        trans.setMerchant_name(data[4]);
        trans.setUser_name(sharedpreferences.getString("username",""));

        Log.e(TAG, "paymentinitail: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);

        mref.push();
        mref = database.getReference("Transaction_subsciption/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
        Log.e(TAG, "paymentfail: ");
        Intent intent = new Intent(Subsciption_Payment2.this, FailureActivity.class);
//        intent.putExtra("data", data1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void paymentpending()
    {
        Sub_data trans = new Sub_data();
        trans.setStartime(0);
        trans.setEndtime(0);
        if(!clicked) {
            trans.setActual_amount(Double.valueOf(Amount));
            trans.setAmount_paid(Double.valueOf(Amount));
        }
        else{
            trans.setActual_amount(Double.valueOf(Amount-50));
            trans.setAmount_paid(Double.valueOf(Amount-50));
        }
        trans.setCoin_earned( 0);
        trans.setCoin_spent(0);
        trans.setTran_ref(tranref);
        trans.setMerchant_uid(data1[5]);
        trans.setUser_uid(mauth.getUid());
        trans.setStatus("Pending");
        trans.setMode(paymentmode);
        trans.setPayment_type("Subscription");
        trans.setTimestamp(timestamp);
        trans.setMerchant_name(data1[0]);
        trans.setUser_name(sharedpreferences.getString("username",""));

        Log.e(TAG, "paymentpending: "+paymentmode );


        mref = database.getReference("Transaction_History_user/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);

        mref.push();
        mref = database.getReference("Transaction_subsciption/"+mauth.getUid()+"/"+tranref);
        mref.setValue(trans);
        mref.push();
        Log.e(TAG, "paymentpending: " );
        Intent intent = new Intent(Subsciption_Payment2.this, PendingActivity.class);
//        intent.putExtra("data", data1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
