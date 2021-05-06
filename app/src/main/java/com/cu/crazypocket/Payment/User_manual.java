package com.cu.crazypocket.Payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_manual extends AppCompatActivity {
    AutoCompleteTextView auto;
    DatabaseReference skref;
    String str, meruid;
    FirebaseDatabase database;
    FirebaseAuth mAuth1;
    DatabaseReference mRef1;
    ListView listView;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);
        auto=(AutoCompleteTextView)findViewById(R.id.searchupi1);
        auto.requestFocus();
        database = FirebaseDatabase.getInstance();
        mAuth1 = FirebaseAuth.getInstance();
        proceed=(Button)findViewById(R.id.button7);



        skref=FirebaseDatabase.getInstance().getReference("Merchant_data");
        listView=(ListView)findViewById(R.id.listviewnumbers);
        popsearch();


        String search=getIntent().getStringExtra("auto");
        auto.setText(search);
        proceed = findViewById(R.id.button7);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = auto.getText().toString();

                if (!str.equals("")) {
                    mRef1 = database.getReference("Merchant_search/" + str);
                    mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot12) {
                            meruid = dataSnapshot12.getValue(String.class);

                            mRef1 = database.getReference("Merchant_data/" + meruid);
                            mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    try {
                                        Provider_data data1 = dataSnapshot.getValue(Provider_data.class);
                                        String OwnerName = data1.getOwnername();
                                        String PhoneNumber = data1.getPhoneno();
                                        String ShopName = data1.getShopname();
                                        String Location = data1.getLocation();
                                        Log.e("TAG", "meruid: " + meruid);
                                        String tranid;
                                        try {
                                            tranid = data1.getData().getPaytm();
                                        } catch (Exception e) {
                                            tranid = "0";
                                        }
                                        final String[] information = new String[]{ShopName, OwnerName, PhoneNumber, Location, data1.getData().getUpi(), meruid, tranid};
                                        Intent intent = new Intent(User_manual.this, PaymentClass.class);
                                        intent.putExtra("Information", information);
                                        intent.putExtra("from", "payagain");
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Log.e("TAG", "onDataChange: " + e.getMessage());
                                        Toast.makeText(User_manual.this, "Not Found!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    private void popsearch() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String n = d.child("phoneno").getValue(String.class);
                        arrayList.add(n);
                    }
                    ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
                    auto.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };skref.addListenerForSingleValueEvent(valueEventListener);
    }

}