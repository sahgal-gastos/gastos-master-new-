package com.cu.crazypocket.Provider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.Data.Provider_data_data;
import com.cu.crazypocket.Payment.PaymentClass;
import com.cu.crazypocket.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class fragment_partners extends Fragment {

    private ArrayList<Provider_data> listitems = new ArrayList<>();
    private ArrayList<String> spinnerlist = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> adapter;
    private SearchView mySearchView;
    private SearchView.OnQueryTextListener queryTextListener;
    Toolbar toolbar;
    DatabaseReference myRef;
    Spinner type_spinner;
    Partner_adapter mAdapter;
    list_adapter customAdapter;
    FirebaseDatabase database;
    View view1,view2;


    SharedPreferences sharedPreferences;


    private ShimmerFrameLayout mShimmerViewContainer;

    int pos ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_partners, container, false);

        type_spinner = v.findViewById(R.id.spin);

        mShimmerViewContainer = v.findViewById(R.id.shimmer_view_container);



        listView = v.findViewById(R.id.partners_list);

        listView.setAdapter(null);
        database = FirebaseDatabase.getInstance();
        customAdapter = new list_adapter(getActivity().getBaseContext(), listitems);
        listView.setAdapter(customAdapter);



        adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,
                        spinnerlist);


            myRef = database.getReference("Location/");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                if(data.getValue(Boolean.class)){
                    spinnerlist.add(data.getKey());
                }


                }
                if(spinnerlist.size()<1)
                spinnerlist.add("Please Wait");
                else{
                    spinnerlist.remove("Please Wait");
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type_spinner.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(spinnerlist.size()<1)
            spinnerlist.add("Please Wait");
        else{
            spinnerlist.remove("Please Wait");
        }
        view1 =null;

        final String[] positions = null;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Provider_data data = customAdapter.getmFilteredList().get(position);
                Double Latitude=data.getData().getLatitude();
                Double Longitude=data.getData().getLongitude();

                String geoUri = "http://maps.google.com/maps?q=loc:" + Latitude.toString() + "," + Longitude.toString() + "(" + data.getShopname()  + ")";

                 Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(geoUri));
                startActivity(intent);

                return false;
            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                TextView txt = view.findViewById(R.id.paynow_option);
                 if(txt.getVisibility() == View.VISIBLE){
                    txt.setVisibility(View.GONE);
                }else{
                    txt.setVisibility(View.VISIBLE);
                }

             //   String UpiId = data.getData().getUpiid();


                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String UpiId = "";
                        Log.e(TAG, "pay: "+customAdapter.getmFilteredList().size() );

                        Provider_data data = customAdapter.getmFilteredList().get(pos);
                        String OwnerName = data.getOwnername();
                        String PhoneNumber = data.getPhoneno();
                        String ShopName = data.getShopname();
                        String Location = data.getLocation();
                        UpiId = data.getData().getUpi();
                        Log.e("INFORMATION", OwnerName + PhoneNumber + ShopName + Location+UpiId);
                        String tranid;
                        try {
                            tranid =data.getData().getPaytm();
                        }catch (Exception e){
                            tranid = "0";
                        }

                        final String[] information = new String[]{ShopName, OwnerName, PhoneNumber, Location, UpiId,data.getMerchant_uid(),tranid};


                        Intent intent = new Intent(getContext(), PaymentClass.class);
                        intent.putExtra("Information", information);
                        intent.putExtra("from", "list");
                        startActivity(intent);

                    }
                });

            }
        });


        final ArrayList<Provider_data> data = new ArrayList<>(listitems);


        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: "+spinnerlist.get(position) );
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                if(position==0)
                {

                    myRef = database.getReference("/Location-based/");
                   myRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           listitems.clear();
                           for (DataSnapshot snapshot1:snapshot.getChildren())
                           {
                                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                                    {
                                        listView.invalidateViews();
                                        Provider_data data1 = snapshot2.getValue(Provider_data.class);
                                        try{
                                            Log.e(TAG, "onDataChange: "+data1.getData().getUpi() );
                                            if(data1.getData().getUpi().length()<5){
                                                throw new Exception() ;
                                            }
                                        }catch (Exception e){
                                            Provider_data_data data12 = new Provider_data_data();
                                            data12.setUpi("UPI Not Present");
                                            data1.setData(data12) ;
                                        }

                                        data1.setMerchant_uid(snapshot2.getKey()+" ");
                                        listitems.add(data1);

                                    }
                               customAdapter.notifyDataSetChanged();

                               mShimmerViewContainer.stopShimmerAnimation();
                               mShimmerViewContainer.setVisibility(View.GONE);

                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                }
                else
                {
                    listupdate( spinnerlist.get(position)); // Did this thing //position tha phele index
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        // search view logic
        search((SearchView) v.findViewById(R.id.search_id));
//
//        mySearchView = v.findViewById(R.id.search_id);
//
//        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                // Here implement search logic
//                if(customAdapter != null){
//                    customAdapter.getFilter().filter(s);
//                    customAdapter.notifyDataSetChanged();
//                }
//
//                return false;
//            }
//        });


        return v;
    }

    private void listupdate(String Location) {
        myRef = database.getReference("/Location-based/"+Location);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listitems.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

//                    Log.e("TAG", "onDataChange: 1" + data1.getData().getUpiid());
//                    Log.e("TAG", "onDataChange: 1" + data.getValue().toString());
                    listView.invalidateViews();
                    Provider_data data1 = data.getValue(Provider_data.class);

                    try{
                        Log.e(TAG, "onDataChange: "+data1.getData().getUpi() );
                        if(data1.getData().getUpi().length()<5){
                            throw new Exception() ;
                        }
                    }catch (Exception e){
                        Provider_data_data data12 = new Provider_data_data();
                        data12.setUpi("UPI Not Present");
                        data1.setData(data12) ;
                    }

                    data1.setMerchant_uid(data.getKey()+" ");
                    listitems.add(data1);

                }
                customAdapter.notifyDataSetChanged();

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onQueryTextChange: "+newText );
                if (customAdapter != null) customAdapter.getFilter().filter(newText);
                return true;
            }
        });
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