package com.cu.crazypocket.Payment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
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

public class PaymentClass extends AppCompatActivity   {


    CardView payment_detail_card;
    CardView payment_card;

    Button proceedbtn;
    Animation fromtop;


    ImageView gpayselect,paytmselect,phonepeselect,bhimselect, amazonselect;
    ImageView gpayselectimg,paytmselectimg,phonepeselectimg,bhimselectimg,amazonselectimg;
    String selected="100";
    // Details
    TextView mShopName, mOwnerName, mLocation, mPhoneNumber, mUpiAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;
        int height = size. y;
        Log. e("Width", "" + width);
        Log. e("height", "" + height);

        Bundle bundle = getIntent().getExtras();

        final String[] data = bundle.getStringArray("Information");



        assert  data != null;
        String ShopName = data[0];
        final String OwnerName = data[4];
        String PhoneNumber = data[2];
        String Location = data[3];
        String Upi =  data[1];

        final String tranid =  data[6];
//        Toast.makeText(PaymentClass.this,tranid,Toast.LENGTH_LONG).show();

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
Toast.makeText(PaymentClass.this,"There is a risk of payment via PhonePe as the Provider hasn't updated the transaction ID in profile. Please ask the Provider for the same.",Toast.LENGTH_LONG).show();
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

        // setting up details
        mShopName.setText(ShopName);
        mOwnerName.setText(OwnerName);
        mPhoneNumber.setText(PhoneNumber);
        mLocation.setText(Location);
        mUpiAddress.setText(Upi);


        proceedbtn = findViewById(R.id.proceed_btn_id);
        payment_detail_card = findViewById(R.id.cardView10);
        payment_card = findViewById(R.id.card_);

        fromtop = AnimationUtils.loadAnimation(this, R.anim.from_top);

        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!data[4].equals("UPI Not Present")&&data[4].length()>4)
            { if(selected.equals("100")){
                Toast.makeText(PaymentClass.this,"Select Your Payment Method",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Intent intent = new Intent(PaymentClass.this, PaymentClass2.class);

                Pair[] pair = new Pair[6];
                pair[0] = new Pair<View, String>(payment_detail_card, "card_transition");
                pair[1] = new Pair<View, String>(mOwnerName, "owner_name_transition");
                pair[2] = new Pair<View, String>(mLocation, "location_transition");
                pair[3] = new Pair<View, String>(mPhoneNumber, "phone_number_transition");
                pair[4] = new Pair<View, String>(mShopName, "shop_name_transition");
                pair[5] = new Pair<View, String>(mUpiAddress, "upi_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PaymentClass.this, pair);
//                    Toast.makeText(PaymentClass.this,"selected"+selected,Toast.LENGTH_SHORT).show();
                intent.putExtra("data", data);
                intent.putExtra("paymentmode", selected);
                startActivity(intent, options.toBundle());}
            }else{
                    Toast.makeText(PaymentClass.this,"Merchant is not accepting payment ",Toast.LENGTH_SHORT).show();
                }

             }
        });

    }
}
