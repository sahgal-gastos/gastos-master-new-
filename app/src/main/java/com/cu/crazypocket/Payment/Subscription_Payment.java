package com.cu.crazypocket.Payment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.cu.crazypocket.R;

public class Subscription_Payment extends AppCompatActivity   {


    CardView payment_detail_card;
    CardView payment_card;

    Button proceedbtn;
    Animation fromtop;


    ImageView gpayselect,paytmselect,phonepeselect,bhimselect, amazonselect;
    ImageView gpayselectimg,paytmselectimg,phonepeselectimg,bhimselectimg,amazonselectimg;
    boolean clicked =false;
    String selected="100";

    // Details
    TextView mShopName, mOwnerName, mLocation, mPhoneNumber, mUpiAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_payment);

        Bundle bundle = getIntent().getExtras();

        final String[] data = bundle.getStringArray("information");
        Log.e("ASDFSF", data.toString());

        final String tranid =  data[5];

        gpayselect = findViewById(R.id.gpayselect);
        paytmselect = findViewById(R.id.paytmselect);
        phonepeselect = findViewById(R.id.phonepaselect);
        bhimselect = findViewById(R.id.bhimselect);
        gpayselectimg = findViewById(R.id.imageView24);
        paytmselectimg = findViewById(R.id.imageView16);
        phonepeselectimg = findViewById(R.id.imageView20);
        bhimselectimg = findViewById(R.id.imageView18);

        gpayselectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gpayselect.getVisibility() ==View.INVISIBLE){
                    paytmselect.setVisibility(View.INVISIBLE);
                    phonepeselect.setVisibility(View.INVISIBLE);
                    bhimselect.setVisibility(View.INVISIBLE);
                    gpayselect.setVisibility(View.VISIBLE);
                    selected ="gpay";
                }

            }
        });
        paytmselectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paytmselect.getVisibility() ==View.INVISIBLE){
                    bhimselect.setVisibility(View.INVISIBLE);
                    phonepeselect.setVisibility(View.INVISIBLE);
                    gpayselect.setVisibility(View.INVISIBLE);
                    paytmselect.setVisibility(View.VISIBLE);
                    selected ="paytm";

                }

            }
        });
        phonepeselectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonepeselect.getVisibility() ==View.INVISIBLE){
                    bhimselect.setVisibility(View.INVISIBLE);
                    paytmselect.setVisibility(View.INVISIBLE);
                    gpayselect.setVisibility(View.INVISIBLE);
                    phonepeselect.setVisibility(View.VISIBLE);
                    selected ="phonepe";
                    if(tranid.equals("0"))
                        Toast.makeText(Subscription_Payment.this,"There is a risk of payment via PhonePe as the Provider hasn't updated the transaction ID in profile. Please ask the Provider for the same.",Toast.LENGTH_LONG).show();

                }


            }
        });
        bhimselectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bhimselect.getVisibility() ==View.INVISIBLE){
                    paytmselect.setVisibility(View.INVISIBLE);
                    phonepeselect.setVisibility(View.INVISIBLE);
                    gpayselect.setVisibility(View.INVISIBLE);
                    bhimselect.setVisibility(View.VISIBLE);
                    selected ="bhim";
                }

            }
        });


        mShopName = findViewById(R.id.textView15 );
        mOwnerName = findViewById(R.id.textView23 );
        mLocation = findViewById(R.id.textView21 );
        mPhoneNumber = findViewById(R.id.textView34);
        mUpiAddress = findViewById(R.id.textView35);


        proceedbtn = findViewById(R.id.proceed_btn_id);
        payment_detail_card = findViewById(R.id.cardView10);
        payment_card = findViewById(R.id.card_);

        fromtop = AnimationUtils.loadAnimation(this, R.anim.from_top);

        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected.equals("100")){
                    Toast.makeText(Subscription_Payment.this,"Select Your Payment Method",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(Subscription_Payment.this, Subsciption_Payment2.class);

                    Pair[] pair = new Pair[1];
                    pair[0] = new Pair<View, String>(payment_detail_card, "card_transition");
//                    pair[1] = new Pair<View, String>(mOwnerName, "owner_name_transition");
//                    pair[2] = new Pair<View, String>(mLocation, "location_transition");
//                    pair[3] = new Pair<View, String>(mPhoneNumber, "phone_number_transition");
//                    pair[4] = new Pair<View, String>(mShopName, "shop_name_transition");
//                    pair[5] = new Pair<View, String>(mUpiAddress, "upi_transition");
//
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Subscription_Payment.this, pair);
                    intent.putExtra("data", data);
                    intent.putExtra("paymentmode", selected);
                    startActivity(intent , options.toBundle());
                }

            }
        });

    }
}
