package com.cu.crazypocket.Payment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPaymentActivity extends AppCompatActivity {
    Button proceed;
    static AutoCompleteTextView searchedittxt;
    String str, meruid;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private boolean mLocationPermissionGranted;
    private SurfaceView surfaceView;
    private ListView numberslist;
    ///my new firebase
    //DatabaseReference skref;
    int a = 0;

    ///my new firebase ENdss

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);
        database = FirebaseDatabase.getInstance();
        getLocationPermission();
        mAuth = FirebaseAuth.getInstance();
        searchedittxt = (AutoCompleteTextView) findViewById(R.id.searchupi);
        // numberslist=(ListView)findViewById(R.id.listviewsnumbers);

        ///my new firebase
        //skref=FirebaseDatabase.getInstance().getReference("Merchant_data");


        searchedittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(NewPaymentActivity.this, useropen.class);
                startActivity(it);

            }
        });
        surfaceView = findViewById(R.id.surface);
        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(NewPaymentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {


            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if (qrcode.size() != 0) {

                    searchedittxt.post(new Runnable() {
                        @Override
                        public void run() {

                            String input = qrcode.valueAt(0).displayValue;
                            Pattern pattern = Pattern.compile("[a-zA-z0-9]+@[a-zA-z0-9]+");
                            Matcher matcher = pattern.matcher(input);
                            if (matcher.find()) {
                                NewPaymentActivity.searchedittxt.setText(matcher.group(0));
                                str = searchedittxt.getText().toString();

                                while (a != 1) {
                                    fan();
                                    a = 1;

                                }


                            }


                        }

                    });


                }


            }
        });


    }

    public void fan() {

        mRef = database.getReference("Merchant_search/" + str);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot12) {
                meruid = dataSnapshot12.getValue(String.class);

                mRef = database.getReference("Merchant_data/" + meruid);
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            Intent intent = new Intent(NewPaymentActivity.this, PaymentClass.class);
                            intent.putExtra("Information", information);
                            intent.putExtra("from", "payagain");
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e("TAG", "onDataChange: " + e.getMessage());
                            Toast.makeText(NewPaymentActivity.this, "Not Found!", Toast.LENGTH_SHORT).show();
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

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        String[] permission = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission[0])
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    permission,
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    onRestart();
                }
            }
        }
    }





    //////
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }




}
