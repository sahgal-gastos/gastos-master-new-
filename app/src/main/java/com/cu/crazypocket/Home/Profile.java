package com.cu.crazypocket.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.cu.crazypocket.Data.OwnerData;
import com.cu.crazypocket.Data.Sub_data;
import com.cu.crazypocket.Data.Userinfo;
import com.cu.crazypocket.LoginActivity;
import com.cu.crazypocket.Payment.Subscription_Payment;
import com.cu.crazypocket.R;
import com.cu.crazypocket.Web.DynamicWebView;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Profile extends AppCompatActivity {

    DatabaseReference ref;
    // Membership Card
    TextView txt1, txt2;

    TextView ActiveOrNot, ValidTill, ActiveNowBtn;
    CardView MemberShipCard;
    ImageView img;
boolean subscription=false;          //initially false
    ImageView Logout;
    String refcode = "";
    long a=0;

    TextView servicestatus;
    TextView userName , userEmail , userPhone , payment_text,valittill;

    ArrayList<Sub_data> sublist;
    SharedPreferences sharedpreferences;

    DateFormat df;
    Date result;
    CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
        int coins;

    Button ReferAndEarnButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        valittill = findViewById(R.id.textView20);
        Logout = findViewById(R.id.Logout);
        txt1 = findViewById(R.id.textView19);
        txt2 = findViewById(R.id.textView29);
        ActiveOrNot = findViewById(R.id.active_deactive_id);
        ValidTill = findViewById(R.id.textView20);
        ActiveNowBtn = findViewById(R.id.textView30);
        MemberShipCard = findViewById(R.id.cardView7);
        img = findViewById(R.id.image_);
        sublist = new ArrayList<>();
        sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);



        df = new SimpleDateFormat("dd MMM yyyy");


        final Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this , R.color.background));
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                firebaseAuth = FirebaseAuth.getInstance();
                                sharedpreferences.edit().clear();
                                callbackManager = CallbackManager.Factory.create();
                                mGoogleSignInClient = GoogleSignIn.getClient(Profile.this,  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(getString(R.string.default_web_client_id)).build());
                                mGoogleSignInClient.signOut();
                                firebaseAuth.signOut();
                                LoginManager.getInstance().logOut();
                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                //if user is signed in, we call a helper method to save the user details to Firebase
                                if (user == null) {
                                    // User is signed in
                                    // you could place other firebase code
                                    //logic to save the user details to Firebase
                                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });



                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout?");
                alert.show();




            }
        });
        database=  FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();

        //mycode free

        ref = database.getReference("Transaction_History_user/"+firebaseAuth.getUid()+"/");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a=snapshot.getChildrenCount();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //mycode free

        ref = database.getReference("Transaction_subsciption/"+firebaseAuth.getUid()+"/");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists())
                    {
                        subscription=true;
                        a=0;
                    }
                    long endtime = Calendar.getInstance().getTimeInMillis();

                    for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                        Sub_data data = data1.getValue(Sub_data.class);
                        data.setEndtime(0); //initially 0
                        sublist.add(data);
                        data = data1.getValue(Sub_data.class);
                        sublist.add(data);


                        Log.e(TAG, "onDataChange: " + (endtime <= data.getEndtime()));
                        try {



                        if (a < 13) {
                            subscription = true;
                        } else {
                            if (data == null) subscription = false;//intially false
                            else {
                                if (endtime <= data.getEndtime()) {
                                    subscription = true;
                                    result = new Date((data.getEndtime()));
                                    valittill.setText(df.format(result));
                                    break;
                                } else

                                    subscription = false;  //initially


                            }
                        }
                    }
                        catch (Exception e) {

                        }

                    }


                    if (subscription) {
                        ActiveOrNot.setText("ACTIVE");
                        ActiveOrNot.setTextColor(Color.parseColor("#67F66D"));
                    } else {

                        if (sublist.size() > 0) {
                            Sub_data data = sublist.get(0);
                            if (data.getEndtime() > 0) {
                                result = new Date((data.getEndtime()));
                                valittill.setText(df.format(result));
                            }
                        }

                        ActiveOrNot.setText("INACTIVE");
                        ActiveOrNot.setTextColor(Color.parseColor("#F44336"));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//////






        userName = findViewById(R.id.textView31);
        userEmail = findViewById(R.id.textView32);
        userPhone = findViewById(R.id.textView33);

          ref = database.getReference("USER_INFO/"+firebaseAuth.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                Userinfo info  = dataSnapshot.getValue(Userinfo.class);
                userName.setText(info.getFirstname()+" "+info.getLastname());
                userEmail.setText(info.getEmail());
                userPhone.setText(info.getPhoneno());
                if(info.getGender().equals("male")){
                    ((ImageView)findViewById(R.id.circleImageView2)).setImageDrawable(ContextCompat.getDrawable(Profile.this, R.drawable.ic_boy)  );
                }else{

                    ((ImageView)findViewById(R.id.circleImageView2)).setImageDrawable(ContextCompat.getDrawable(Profile.this, R.drawable.ic_girl)  );
                }
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }
        });




        findViewById(R.id.Legaltext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profile.this , DynamicWebView.class);
                intent.putExtra("Mode","Legal");
                startActivity(intent);
            }
        });
        findViewById(R.id.textView17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profile.this , DynamicWebView.class);
                intent.putExtra("Mode","Contact_us");
                startActivity(intent);
            }
        });



        MemberShipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt1.getVisibility() == View.VISIBLE){
                    ActiveOrNot.setVisibility((View.GONE));
                    txt1.setVisibility(View.GONE);
                    txt2.setVisibility(View.GONE);
                    ActiveNowBtn.setVisibility(View.GONE);
                    ActiveNowBtn.setVisibility(View.GONE);
                    ValidTill.setVisibility(View.GONE);

                    img.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_24px));

                }else{
                    ActiveOrNot.setVisibility((View.VISIBLE));
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    if(!subscription)
                    ActiveNowBtn.setVisibility(View.VISIBLE);
                    else
                    ValidTill.setVisibility(View.VISIBLE);

                    img.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));

                }

            }
        });

        if(MemberShipCard.getVisibility() == View.VISIBLE){

            ActiveNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       coins = sharedpreferences.getInt("coinearned",0);

                     ref = database.getReference("Owner");
                     ref.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             OwnerData  data1 = dataSnapshot.getValue(OwnerData.class);
                             String tranid;
                             try {
                                 tranid =data1.getTransection_id();
                             }catch (Exception e){
                                 tranid = "0";
                             }

                             String[] data = new String[]{String.valueOf(coins),data1.getUpi(),data1.getTransection_id(),String.valueOf(data1.getAmount()),data1.getName(),tranid};

                             Intent intent = new Intent(Profile.this, Subscription_Payment.class);
                             Log.e("PROFILE" , data.toString());
                             intent.putExtra("information", data);
                             startActivity(intent);

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });

                }
            });

            findViewById(R.id.rateus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });

            ReferAndEarnButton = findViewById(R.id.button4);

            ReferAndEarnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = userName.getText().toString().trim();

                    String[] arr = name.split(" ");

                    final String RefCode =sharedpreferences.getString("Ref","");


                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.referpopup, null);
                    builder.setView(customLayout);


                    TextView mRefCode = customLayout.findViewById(R.id.textView56);
                    ImageView mCopyCode = customLayout.findViewById(R.id.imageView2);
                    Button sharebtn = customLayout.findViewById(R.id.button9);

                    mRefCode.setText(RefCode);

                    mCopyCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clipData = android.content.ClipData.newPlainText("Text Label", RefCode);
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getApplicationContext(),"Referral Code Copied!",Toast.LENGTH_SHORT).show();

                        }
                    });


                    sharebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//123123123

                            ref = database.getReference("/link");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String RefMessage = "Hey buddy! \n" +
                                            "Apply my referral code \""+ RefCode  + "\" while signing up on GASTOS and GET 50 COINS in Your Account so, Don't forget to apply the code during sign-up! \n" +
                                            "Collect 250 for FLAT Rs 50 OFF on GASTOS Account.\n" +
                                            "Click here to download the app :"+dataSnapshot.getValue(String.class) ;

                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, RefMessage);
                                    sendIntent.setType("text/plain");

                                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                                    startActivity(shareIntent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

        }
}
}
