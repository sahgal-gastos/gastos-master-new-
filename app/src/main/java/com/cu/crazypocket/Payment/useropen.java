package com.cu.crazypocket.Payment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class useropen extends AppCompatActivity {
    DatabaseReference refrence;
    AutoCompleteTextView autoCompleteTextView;
    ListView numebrs;
    Button myprocedd;
    String mystr, mymeruid;
    FirebaseDatabase mydatabse;
    FirebaseAuth myauthenication;
    DatabaseReference myrefrence;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useropen);
        mydatabse = FirebaseDatabase.getInstance();
        myauthenication = FirebaseAuth.getInstance();
        refrence= FirebaseDatabase.getInstance().getReference("Merchant_data");
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.searchauto);
        autoCompleteTextView.requestFocus();

        numebrs=(ListView)findViewById(R.id.simplenumbers);


        popsearch();
        myprocedd = findViewById(R.id.useropenbutton);
        myprocedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mystr = autoCompleteTextView.getText().toString();

                if (!mystr.equals("")) {
                    myrefrence = mydatabse.getReference("Merchant_search/" + mystr);
                    myrefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot12) {
                            mymeruid = dataSnapshot12.getValue(String.class);

                            myrefrence = mydatabse.getReference("Merchant_data/" + mymeruid);
                            myrefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    try {
                                        Provider_data data1 = dataSnapshot.getValue(Provider_data.class);
                                        String OwnerName = data1.getOwnername();
                                        String PhoneNumber = data1.getPhoneno();
                                        String ShopName = data1.getShopname();
                                        String Location = data1.getLocation();
                                        Log.e("TAG", "meruid: " + mymeruid);
                                        String tranid;
                                        try {
                                            tranid = data1.getData().getPaytm();
                                        } catch (Exception e) {
                                            tranid = "0";
                                        }
                                        final String[] information = new String[]{ShopName, OwnerName, PhoneNumber, Location, data1.getData().getUpi(), mymeruid, tranid};
                                        Intent intent = new Intent(useropen.this, PaymentClass.class);
                                        intent.putExtra("Information", information);
                                        intent.putExtra("from", "payagain");
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Log.e("TAG", "onDataChange: " + e.getMessage());
                                        Toast.makeText(useropen.this, "Not Found!", Toast.LENGTH_SHORT).show();
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





        /////


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
                    autoCompleteTextView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };refrence.addListenerForSingleValueEvent(valueEventListener);
    }



}