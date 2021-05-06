package com.cu.crazypocket.Provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.R;

import java.util.ArrayList;

public class Partner_adapter extends RecyclerView.Adapter<Partner_adapter.ViewHolder> implements Filterable {
    private ArrayList<Provider_data> mArrayList;
    private ArrayList<Provider_data> mFilteredList;

    public Partner_adapter(ArrayList<Provider_data> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public Partner_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Partner_adapter.ViewHolder viewHolder, int i) {

        viewHolder.shop_name.setText(mFilteredList.get(i).getShopname());
        viewHolder.shop_add.setText(mFilteredList.get(i).getAddress());



     }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Provider_data> filteredList = new ArrayList<>();

                    for (Provider_data androidVersion : mArrayList) {

                        if (androidVersion.getShopname().toLowerCase().contains(charString) || androidVersion.getPhoneno().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Provider_data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView shop_name,shop_add;
        public ViewHolder(View view) {
            super(view);

            shop_name = (TextView)view.findViewById(R.id.shop_name);
            shop_add = (TextView)view.findViewById(R.id.shop_add);

        }
    }

}