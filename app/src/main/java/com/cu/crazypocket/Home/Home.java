package com.cu.crazypocket.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cu.crazypocket.Deals.fragment_deals;
import com.cu.crazypocket.LoginActivity;
import com.cu.crazypocket.Provider.fragment_partners;
import com.cu.crazypocket.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigation;
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem;
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener;

import static com.google.android.gms.common.util.CollectionUtils.listOf;

public class Home extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth  ;

    FloatingActionButton fbtn;

    private ViewPager viewPager;
    MenuItem prevMenuItem;

    //Fragments
    fragment_deals dealsFragment;
    fragment_home homeFragment;
    fragment_partners partnersFragment;
    FluidBottomNavigation fluidBottomNavigation;

    PathModel outline;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private float mYVal;
    private RelativeLayout mlinId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();

        fluidBottomNavigation = findViewById(R.id.fluidBottomNavigation);

        final Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#08313A"));


        fluidBottomNavigation.setAccentColor(Color.parseColor("#08313A"));
        fluidBottomNavigation.setBackColor(ContextCompat.getColor(this, R.color.colorAccent));
        fluidBottomNavigation.setTextColor(Color.parseColor("#08313A"));
        fluidBottomNavigation.setIconColor(ContextCompat.getColor(this, R.color.colorBackground));
        fluidBottomNavigation.setIconSelectedColor(ContextCompat.getColor(this, R.color.iconSelectedColor));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    new fragment_home()).commit();
            fluidBottomNavigation.selectTab(1);
        }

        fluidBottomNavigation.setItems(listOf(
                new FluidBottomNavigationItem(
                        getString(R.string.deals),
                        ContextCompat.getDrawable(this, R.drawable.ic_dealz)),
                new FluidBottomNavigationItem(
                        getString(R.string.home),
                        ContextCompat.getDrawable(this, R.drawable.ic_home_black_24dp)),
                new FluidBottomNavigationItem(
                        getString(R.string.provider),
                        ContextCompat.getDrawable(this, R.drawable.ic_providers))
        ));


        fluidBottomNavigation.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {

                Fragment fragment = null;

                if(i == 0){
                    fragment = new fragment_deals();
                    window.setStatusBarColor(Color.parseColor("#08313A"));
                }

                if(i == 1){
                    window.setStatusBarColor(ContextCompat.getColor(Home.this , R.color.background));
                    fragment = new fragment_home();
                }

                if(i == 2){

                    fragment = new fragment_partners();
                    window.setStatusBarColor(Color.parseColor("#08313A"));
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        fragment).commit();

            }
        });



        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user == null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.d("TAG", "onAuthStateChanged:signed_out:" );
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_in");
                }
            }};
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//        if (firebaseAuth.getCurrentUser() == null) {
//            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//         }
    }
    @Override
    protected void onStop() {
        super.onStop();
//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
    }
}