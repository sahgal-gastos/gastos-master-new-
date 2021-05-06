package com.cu.crazypocket.Payment;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cu.crazypocket.Payment.Payment_options;
import com.cu.crazypocket.R;

import java.util.ArrayList;

public class Payment_list_Adapter implements ListAdapter {

    Context context;
    ArrayList<Payment_options> arrayList;

    public Payment_list_Adapter(Context context, ArrayList<Payment_options> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
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

        Payment_options options = arrayList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.payment_list, null);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent(context , PaymentClass.class);
//                    context.startActivity(intent);

                }
            });
            TextView tittle = convertView.findViewById(R.id.shop_name);

            tittle.setText(options.getPaymentoption_name());

        }
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
