package com.cu.crazypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cu.crazypocket.Home.Home;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuccessActivity extends AppCompatActivity {

    TextView mShopName ,  amounttxt,timetxt,billtxt ,datetxt;
    Button donetbn;
    String ShopName  ;
    String OwnerName  ;
    DateFormat df;
    Date result;
    String PhoneNumber  ;
    String Location  ;
    String Upi  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Bundle bundle = getIntent().getExtras();

        String[] data = bundle.getStringArray("data");
        amounttxt = findViewById(R.id.textView46);
        timetxt = findViewById(R.id.textView48);
        billtxt = findViewById(R.id.textView47);
        datetxt = findViewById(R.id.textView48);
        mShopName = findViewById(R.id.textView45);
        donetbn = findViewById(R.id.button6);
        df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss ");

        if(data[0].equals("Gastos")){
            ShopName = data[0];
            amounttxt.setText("Rs. "+data[1]);
            billtxt.setText("Rs. "+data[1]);
            result = new Date(Long.parseLong(data[2]));
            datetxt.setText(df.format(result));
        }else {
            String[] data1 = bundle.getStringArray("data1");

            ShopName = data[0];
                OwnerName = data[1];
              PhoneNumber = data[2];
              Location = data[3];
              Upi = data[4];
            amounttxt.setText("Rs. "+data1[0]);
            billtxt.setText("Rs. "+data1[1]);
            result = new Date(Long.parseLong(data1[2]));
            datetxt.setText(df.format(result));
        }
        mShopName.setText(ShopName);

        donetbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });

    }
}
