package com.cu.crazypocket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cu.crazypocket.Data.Trans_history;
import com.cu.crazypocket.Data.Userinfo;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    EditText Firstnameview,lastnameview,Emailview,Phonenoview,dobview;
    Button next,Verify;
    CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth firebaseAuth1;
    int verify=0;
    View popupdialog;
    Button verifybtn , canclebtn,velidatebtn;
    EditText otpedittext,refnoedittest;
    TextView resendtext;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    String dob;
    private boolean validated =false;
    private boolean elitevalidated =false;
    private boolean initiate = false;
    private boolean linked = false;
    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    ImageView maleselected,femaleselected;
    ImageView maleimageview,femaleimageview;
    private String mVerificationId;
    String refcode = "";
    long millis;



    String imageresponce;
    int RESULT_LOAD_IMAGE = 1;
    Uri imageuri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth1 = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(Register.this);
        alertDialog = builder.create();

        Log.w("tag",sharedpreferences.getString ("fname","11")+" "+sharedpreferences.getString ("lname","")+sharedpreferences.getString ("email","")+sharedpreferences.getString ("pic",""));
        refnoedittest = findViewById(R.id.refnoedittest);
        velidatebtn  =findViewById(R.id.velidatebutton);
        Verify = findViewById(R.id.nextbutton1);
        next  =  findViewById(R.id.nextbutton);
        Firstnameview = findViewById(R.id.Firstname);
        lastnameview = findViewById(R.id.lastname);
        Emailview  =  findViewById(R.id.Email);
        Phonenoview  = findViewById(R.id.Phoneno);
        dobview      = findViewById(R.id.dob);
        final Userinfo userInfo = new Userinfo();
        final Trans_history trans_history = new Trans_history();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("TAG", "onCreate: "+firebaseAuth.getUid());
        maleselected = findViewById(R.id.maleselected);
        femaleselected = findViewById(R.id.femaleselected);
        maleimageview = findViewById(R.id.maleImageView);
        femaleimageview = findViewById(R.id.femaleImageView);
        maleselected.setVisibility(View.INVISIBLE);
        femaleselected.setVisibility(View.INVISIBLE);

        maleimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                if(femaleselected.getVisibility()==View.VISIBLE){
                    femaleselected.setVisibility(View.INVISIBLE);
                    femaleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_girl));

                }
                maleselected.setVisibility(View.VISIBLE);




            }
        });


        femaleimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                if( maleselected.getVisibility()==View.VISIBLE){
                    maleselected.setVisibility(View.INVISIBLE);
                    maleimageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_boy));
                }
                femaleselected.setVisibility(View.VISIBLE);

            }
        });
        Firstnameview.setText(sharedpreferences.getString ("fname",""));
        lastnameview.setText(sharedpreferences.getString ("lname",""));
        Emailview.setText(sharedpreferences.getString ("email",""));
  //Commented This

//        Verify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendVerificationCode(Phonenoview.getText().toString());
//
//                Log.e("Phonenoview" , "onCreate: " +Phonenoview.getText().toString() );
//
//                builder = new AlertDialog.Builder(Register.this);
//                builder.setCancelable(false);
//                LayoutInflater layoutInflater = LayoutInflater.from(Register.this);
//
//                popupdialog = layoutInflater.inflate(R.layout.verify_alert, null);
//
//
//                otpedittext = popupdialog.findViewById(R.id.editText2);
//                popupdialog.findViewById(R.id.btnverfy).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick ( View view ) {
//                        if(!otpedittext.getText().toString().trim().equals(""))
//                            verifyVerificationCode(otpedittext.getText().toString().trim());
//                        Log.e("otpedittext", "onClick: " +otpedittext.getText().toString().trim() );
//                    }
//                });
//                popupdialog.findViewById(R.id.btncancle).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
//                popupdialog.findViewById(R.id.resendtextid).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick ( View view ) {
//                        sendVerificationCode(Phonenoview.getText().toString());
//
//                    }
//                });
//                builder.setView(popupdialog);
//
//                alertDialog = builder.create();
//                alertDialog.setTitle("Enter OTP");
//                alertDialog.show();
//
//
//            }
//        });

        //Comented Till here
        dobview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal  =Calendar.getInstance();
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        Log.e("TAG", "onDateSet: "+cal.getTimeInMillis() );

                        dobview.setText(dayOfMonth+"/" +(month + 1) + "/" +year     );
                        dob=(dayOfMonth+"/" +(month + 1) + "/" +year  );

                              millis = cal.getTimeInMillis();

                    }
                }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();



            }
        });
        refnoedittest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {

            }

            @Override
            public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {
                validated =false;
            }

            @Override
            public void afterTextChanged ( Editable editable ) {

            }
        });
        velidatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                refcode = refnoedittest.getText().toString().trim();
                DatabaseReference myRef = database.getReference("/ref_code_elite/" );
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                        Log.e("TAG", "onDataChange:123 "+Long.parseLong(String.valueOf(millis)) );
                        refcode = refnoedittest.getText().toString() .trim();
                        for (DataSnapshot data: dataSnapshot.getChildren()  ) {
                            Log.e("TAG" , "onDataChange: "  +data.getKey(). trim()+"  "+refcode );

                            if(data.getKey() .trim().equals(refcode)){
                                validated =true;
                                elitevalidated =true;
                                Log.e("TAG" , "onDataChange: 2"  +data.getKey() .trim() );

                                break;

                            }
                            Log.e("TAG" , "onDataChange: 1"  +refcode );
                        }
                        if(elitevalidated){
                            Log.e("TAG" , "validate"  +refcode );

                            Toast.makeText(Register.this,"Elite Code applied",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled ( @NonNull DatabaseError databaseError ) {

                    }
                });

                 myRef = database.getReference("/ref_code/" );
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {

                        refcode = refnoedittest.getText().toString().trim();
                        for (DataSnapshot data: dataSnapshot.getChildren()  ) {
                            Log.e("TAG" , "onDataChange: "  +data.getKey() .trim()+"  "+refcode );

                            if(data.getKey(). trim().equals(refcode)){
                                validated =true;
                                Log.e("TAG" , "onDataChange: 2"  +data.getKey().trim() );
                                 break;

                            }
                            Log.e("TAG" , "onDataChange: 1"  +refcode );
                        }
                        if(validated){
                            Toast.makeText(Register.this,"Code applied",Toast.LENGTH_LONG).show();

                        }
                        else{
                            refnoedittest.setError("Invalid Code");
                        }

                    }

                    @Override
                    public void onCancelled ( @NonNull DatabaseError databaseError ) {

                    }
                });

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: "+Phonenoview.getEditableText().toString().trim().length()+"  "+ linked);
                int z=0;
                if(Firstnameview.getEditableText().toString().trim().equals("")){
                    Firstnameview.setError("Please enter Your First name ");
                    z=1;
                }
                if(lastnameview.getEditableText().toString().trim().equals("")){
                    lastnameview.setError("Please enter Your Last name or - ");
                    z=1;
                }
//                if(!linked){
//                    Phonenoview.setError("This Phone no is linked with other account");
//                    z = 1;
//
//                }
                if(Phonenoview.getEditableText().toString().trim().equals("")){
                    Phonenoview.setError("Please enter Your Phone no ");
                    z=1;
                }

                else if((Phonenoview.getEditableText().toString().trim().length()<10 )){
                    Phonenoview.setError("Please enter a Valid Phone no ");
                    z=1;
                }
                else if((Phonenoview.getEditableText().toString().trim().length()>10 )) {
                    Phonenoview.setError("Please enter a Valid Phone no ");
                    z = 1;
                }
//                if(!initiate){    //Commented this Date 23
//                    Phonenoview.setError("Verify Your no");
//                    z = 1;
//
//                }

                if(dobview.getEditableText().toString().trim().equals("")){
                    z = 1;
                    dobview.setError("Select your DOB");
                }
                if((maleselected.getVisibility()==View.INVISIBLE)&&(femaleselected.getVisibility()==View.INVISIBLE)){
                    z = 1;
                    Toast.makeText(Register.this,"Select you Gender by Clicking on your Avatar",Toast.LENGTH_LONG).show();
                }
                if(z==0)
                {

                    userInfo.setFirstname(Firstnameview.getEditableText().toString().trim());
                    userInfo.setLastname(lastnameview.getEditableText().toString().trim());
                    userInfo.setEmail(Emailview.getEditableText().toString().trim());
                    userInfo.setDob(Long.parseLong(String.valueOf(millis)));
                    userInfo.setPhoneno(Phonenoview.getEditableText().toString().trim());
                    userInfo.setRefcode(Firstnameview.getEditableText().toString().trim()+ getAlphaNumericString(6));
                    userInfo.setUserpic("0");

                    if(maleselected.getVisibility()==View.VISIBLE)
                        userInfo.setGender("male");
                    if(femaleselected.getVisibility()==View.VISIBLE)
                        userInfo.setGender("female");
                    DatabaseReference myRef = database.getReference("/USER_INFO/"+firebaseAuth.getUid());
                    Log.e("uid" , "onClick: " +firebaseAuth1.getCurrentUser().getEmail()+" "+ mAuth.getUid()+" ");
                    Log.e("uid" , "onClick: " +firebaseAuth1.getUid()+" "+ mAuth.getCurrentUser().getEmail()+" ");
                    myRef.setValue(userInfo);
                    myRef.push();
                    myRef = database.getReference("/ref_code/"+userInfo.getRefcode());
                    myRef.setValue(true);
                    myRef.push();

                   if(validated)
                    { Calendar c1 = Calendar.getInstance();
                    trans_history.setTimestamp(c1.getTimeInMillis());
                    trans_history.setCoin_spent(0);
                    trans_history.setTotalcoin(0);
                    trans_history.setAmount_paid(0.0);
                    trans_history.setActual_amount(0.0);
                    trans_history.setTotalspent(0);
                    trans_history.setPayment_type("Ref");
                    trans_history.setStatus("Finished");

                    if(elitevalidated)
                    {
                        trans_history.setMode("Elite_Ref");
                        trans_history.setCoin_earned(250);
                        myRef = database.getReference("/Transaction_History_user/"+firebaseAuth.getUid()+"/"+firebaseAuth.getUid() +refnoedittest.getText().toString().trim());
                        myRef.setValue(trans_history);
                        myRef.push();

                    }

                    else
                       { trans_history.setCoin_earned(50);
                           trans_history.setMode("Ref");
                           myRef = database.getReference("/Transaction_History_user/"+firebaseAuth.getUid()+"/"+firebaseAuth.getUid()+ refnoedittest.getText().toString().trim());
                           myRef.setValue(trans_history);
                           myRef.push();


                           myRef = database.getReference("/ref_code_cache/"+refnoedittest.getText().toString().trim()+"/"+ firebaseAuth.getUid()+refnoedittest.getText().toString().trim());
                           myRef.setValue(trans_history);
                           myRef.push();
                       }

}



                }
            }
        });





    }


//Comment from here

//    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91" + mobile,
//                90,
//                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
//                mCallbacks);
//        Log.e(   "VerificationCompleted:","testing"  );
//
//    }



//    //the callback to detect the verification status
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted( PhoneAuthCredential phoneAuthCredential) {
//
//            //Getting the code sent by SMS
//            String code = phoneAuthCredential.getSmsCode();
//
//            //sometime the code is not detected automatically
//            //in this case the code will be null
//            //so user has to manually enter the code
//            if (code != null) {
//
//                Log.e(   "VerificationCompleted:",code  );
//                otpedittext.setText(code);
////                editTextCode.setText(code);
//                //verifying the code
//                verifyVerificationCode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed( FirebaseException e) {
//            Log.e(   "VerificationCompleted:",e.getMessage()  );
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            Log.e(     "onCodeSent: ",s+forceResendingToken.toString() );
//            //storing the verification id that is sent to the user
//            mVerificationId = s;
//        }
//    };
//
//
//    private void verifyVerificationCode(String code) {
//        try{
//            //creating the credential
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code );
//            //signing the user
//            signInWithPhoneAuthCredential(credential);
//
//        }catch (Exception  e){
//            Log.e("error" , "verifyVerificationCode: " +e.getMessage() );
//        }
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//
//
//
//
//        mAuth.getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            linked = true;
//                            Log.d("TAG", "linkWithCredential:success");
//                            alertDialog.cancel();
//                            initiate =  true;
//                            if (linked)
//                                Phonenoview.setEnabled(false);
//                            Toast.makeText(getBaseContext(),"Verified   ",Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Log.w("TAG", "linkWithCredential:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            linked=false;
////                            updateUI(null);
//
//
//
//                            String message = " ";
//
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                message = "Invalid code entered...";
//                            }
//                            Toast.makeText(Register.this, message+"  "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        // ...
//                    }
//                });
//
//    }


//Commented Till here
    @Override
    public void onBackPressed() {
        try {
            if (alertDialog.isShowing())
                alertDialog.dismiss();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.getCurrentUser().unlink(firebaseAuth.getCurrentUser().getProviderId())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w("unlink" , "onComplete: " +"done" );
                        }
                        Log.w("unlink" , "onComplete: " +"failed"+task.getException().getMessage());
                    }
                });


        callbackManager = CallbackManager.Factory.create();
        mGoogleSignInClient = GoogleSignIn.getClient(this,  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).build());
        mGoogleSignInClient.signOut();
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();

        finish();
        super.onBackPressed();
    }

    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            if (alertDialog.isShowing())
//                alertDialog.dismiss();
//        }
//        catch (NullPointerException e){
//            e.printStackTrace();
//        }
//        firebaseAuth = FirebaseAuth.getInstance();
//        callbackManager = CallbackManager.Factory.create();
//        mGoogleSignInClient = GoogleSignIn.getClient(this,  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)).build());
//        mGoogleSignInClient.signOut();
//
//        firebaseAuth.signOut();
//        LoginManager.getInstance().logOut();
//finish();
//    }


}
