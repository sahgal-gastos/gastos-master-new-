package com.cu.crazypocket.Payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cu.crazypocket.Data.Trans_history;
import com.cu.crazypocket.R;

import java.util.List;

public class Payment_listviewadapter extends RecyclerView.Adapter<Payment_listviewadapter.MyViewHolder> {
    private List<Trans_history> list;



    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;

        MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.circleImageView3);
 //            genre = view.findViewById(R.id.genre);
//            year = view.findViewById(R.id.year);
        }
    }
    public Payment_listviewadapter(List<Trans_history> moviesList) {
        this.list = moviesList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_row, parent, false);
 //        itemView.setOnClickListener(this);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Trans_history data = list.get(position);
//        holder.title.setText(data.getShopname());

 //        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
















//
//public class Payment_listviewadapter extends RecyclerView.Adapter implements ListAdapter {
//
//    Context context;
//    ArrayList<Payment_Data> arrayList;
//
//    public Payment_listviewadapter(Context context, ArrayList<Payment_Data> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        return true;
//    }
//
//    @Override
//    public void registerDataSetObserver(DataSetObserver observer) {
//
//    }
//
//    @Override
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount () {
//        return 0;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Payment_Data options = arrayList.get(position);
//        if (convertView == null) {
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            convertView = layoutInflater.inflate(R.layout.payment_row, null);
//
//
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    Intent intent = new Intent(context , PaymentClass.class);
////                    context.startActivity(intent);
//
//                }
//            });
//            TextView tittle = convertView.findViewById(R.id.textView7);
//            TextView amount = convertView.findViewById(R.id.amt_paid);
//
//
//            tittle.setText(options.getShopname());
//            amount.setText(options.getAmount_paid());
//
//        }
//        return convertView;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder ( @NonNull RecyclerView.ViewHolder holder , int position ) {
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//}
