package com.cu.crazypocket.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.Payment.PaymentClass;
import com.cu.crazypocket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tenclouds.fluidbottomnavigation.view.TitleView;

public class popUppp extends AppCompatActivity {
    String name;
    String id;
    Provider_data data4;
    Button button;
    String OwnerName  ;
    String PhoneNumber  ;
    String ShopName  ;
    String Location  ;
    String shopimg  ;
    String paymentType;

    FirebaseDatabase databse;
    DatabaseReference referrenc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_uppp);
        databse = FirebaseDatabase.getInstance();
        TextView Status = (TextView) findViewById(R.id.textView251);
        TextView amountid = (TextView) findViewById(R.id.Amount_Id1);
        TextView acctual = (TextView) findViewById(R.id.textView221);
        TextView modee = (TextView) findViewById(R.id.textView281);
        TextView date = (TextView) findViewById(R.id.textView261);
        TextView payment_type = (TextView) findViewById(R.id.payment_type_id1);
        TextView shopname = (TextView) findViewById(R.id.shop_name_id1);
        button = (Button) findViewById(R.id.button21);


        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("Status");
            String amount = intent.getStringExtra("Amountid");
            String actual = intent.getStringExtra("AcctualAmount");
            String mode = intent.getStringExtra("Mode");
            paymentType = intent.getStringExtra("paymenttype");
            String hint = "User Name";
            String merchantname = intent.getStringExtra("merchantname");
            String datemy = intent.getStringExtra("date");
           id = intent.getStringExtra("id");

            Status.setText(name);
            amountid.setText(amount);
            acctual.setText(actual);
            modee.setText(mode);
            shopname.setText(merchantname);
            payment_type.setText(paymentType);
            date.setText(datemy);

        }
        if (name.equals("Success")) {
            Status.setTextColor(Color.parseColor("#237f52"));
            button.setVisibility(View.GONE);
            button.setEnabled(false);

        }
        if (name.equals("Failed")) {
            Status.setTextColor(Color.parseColor("#cf142b"));
        }
        if (name.equals("Pending")) {
            Status.setTextColor(Color.parseColor("#FFCC00"));
        }

        if(id!=null) {
            referrenc = databse.getReference("Merchant_data/" + id);
            referrenc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data4 = snapshot.getValue(Provider_data.class);
                    if (!paymentType.equals("Subscription")) {
                        try
                        {String st = data4.getShoppic();

                            if(st.length()<50)
                                throw new Exception();
                            byte[] decodedString = Base64.decode(st, Base64.DEFAULT);
                            Bitmap decodedbit = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            //((ImageView) customLayout.findViewById(R.id.shopimage)).setImageBitmap(decodedbit);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert data4 != null;
                OwnerName =  data4.getOwnername();
                PhoneNumber = data4.getPhoneno();
                ShopName = data4.getShopname();
                Location = data4.getLocation();
                shopimg = data4.getShopname();
                String tranid;
                try {
                    tranid =data4.getData().getPaytm();
                }catch (Exception e){
                    tranid = "0";
                }
                final String[] information = new String[]{ShopName, OwnerName, PhoneNumber, Location,data4.getData().getUpi() , id,tranid};
                Intent intent = new Intent(getApplicationContext(), PaymentClass.class);
                intent.putExtra("Information", information);
                intent.putExtra("from", "payagain");
                startActivity(intent);

            }
        });
    }


}