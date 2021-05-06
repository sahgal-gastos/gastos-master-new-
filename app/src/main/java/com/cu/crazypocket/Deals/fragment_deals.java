package com.cu.crazypocket.Deals;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cu.crazypocket.Data.Deals_data;
import com.cu.crazypocket.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class fragment_deals extends Fragment {


    ArrayList<Bitmap> listitems = new ArrayList<>();
    ImageView information;

    View popupcontrol;
    Button close_btn;
    TextView information_text;

    ListView deals_list;

    ArrayList<Deals_data> deals_data;


    private ShimmerFrameLayout mShimmerViewContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deals, container, false);


        mShimmerViewContainer = v.findViewById(R.id.shimmer_view_container);

        deals_list = v.findViewById(R.id.dealz_list);

         deals_data = new ArrayList<>();

        FirebaseDatabase database =   FirebaseDatabase.getInstance();
        DatabaseReference ref  = database.getReference("Deals_data/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    Log.e(TAG , "onDataChange: " +dataSnapshot1.getValue(Deals_data.class).getTitle() );



                 deals_data.add( dataSnapshot1.getValue(Deals_data.class));

                }
                Collections.sort(deals_data);
                deals_adapter deals_data1 = new deals_adapter(deals_data , getActivity() );

                deals_list.setAdapter(deals_data1);

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }
        });






        return v;
    }

    private void setPopupcontrol(){

        LayoutInflater inflater = LayoutInflater.from(getContext());

        popupcontrol = inflater.inflate(R.layout.deals_popup , null);

        close_btn = popupcontrol.findViewById(R.id.close_btn);
        information_text = popupcontrol.findViewById(R.id.text_deals);

    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
