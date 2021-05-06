package com.cu.crazypocket.Deals;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cu.crazypocket.Data.Deals_data;
import com.cu.crazypocket.R;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.BitSet;

import jp.wasabeef.blurry.Blurry;

public class deals_adapter implements ListAdapter{

    ArrayList<Deals_data> arrayList;
    Context context;



    public deals_adapter(ArrayList<Deals_data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Deals_data subjectData = arrayList.get(position);

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.deals_list_row, null);


        }

        TextView deals_name = convertView.findViewById(R.id.textView16);
        ImageView deals_img = convertView.findViewById(R.id.offer_img);



        deals_name.setText(subjectData.getTitle());

        byte[] decodedString = Base64.decode(subjectData.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       // deals_img.setImageBitmap(decodedByte);



        Blurry.with(context).from(decodedByte).into(deals_img);


        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
