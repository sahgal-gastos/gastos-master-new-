package com.cu.crazypocket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cu.crazypocket.Home.Home;

public class FailureActivity extends AppCompatActivity {
    TextView mShopName;
    Button donetbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);



//        Bundle bundle = getIntent().getExtras();

//        String[] data = bundle.getStringArray("data");

        mShopName = findViewById(R.id.textView45);
        donetbn = findViewById(R.id.button6);

//        assert  data != null;
//        String ShopName = data[0];
//        final String OwnerName = data[1];
//        String PhoneNumber = data[2];
//        String Location = data[3];
//        String Upi =  data[4];

//        mShopName.setText(ShopName);

        donetbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FailureActivity.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });
    }
}
