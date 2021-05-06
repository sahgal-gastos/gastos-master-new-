package com.cu.crazypocket.Home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cu.crazypocket.Data.Trans_history;
import com.cu.crazypocket.R;

import java.util.ArrayList;

public class Recycleitemlongclickadapter  extends RecyclerView.Adapter<Recycleitemlongclickadapter.MyViewHolder> {

     private ArrayList<Trans_history> list_members=new ArrayList<>();
    private final LayoutInflater inflater;
    View view;
    MyViewHolder holder;
    private Context context;


    public Recycleitemlongclickadapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }


    //This method inflates view present in the RecyclerView
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.payment_row, parent, false);
        holder=new MyViewHolder(view);
        return holder;
    }

    //Binding the data using get() method of POJO object
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       try{ Trans_history list_items=list_members.get(position);

       if(list_items.getMode().equals("Subscription"))
           holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.mipmap.ic_launcher));

        else if(list_items.getMode().equals("gpay"))
        holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.gpay));
        else if(list_items.getMode().equals("paytm"))
            holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.paytm2));
        else if(list_items.getMode().equals("bhim"))
            holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.bhim2));
        else if(list_items.getMode().equals("phonepe"))
            holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.phonepe));
        else if(list_items.getMode().equals("Ref"))
            holder.imageView.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.ic_coin));

        String ShopName = list_items.getMerchant_name();

        char[] chararr = ShopName.toCharArray();

        char startchar = chararr[0];

        char endchar = 0;

           String reverse = inPlaceReverse(ShopName);

//           Log.e("DSFSFSf", reverse);

           for(int i =0;i < reverse.length() - 1; i ++){
//               Log.e("ERERERERE" , String.valueOf(reverse.charAt(i)));
               if(reverse.charAt(i) != ' ' && reverse.charAt(i + 1) == ' '){
                   endchar = reverse.charAt(i);
                   break;
               }
           }

//           if(startchar >= 'a' && startchar <= 'z'){
//               startchar = (char)(startchar - 32);
//           }
//
//           if(endchar >= 'a' && endchar <= 'z'){
//               endchar = (char)(endchar - 32);
//           }

           String initials = String.valueOf(startchar) + String.valueOf(endchar);



//        Log.e("INITIALS", initials);
           if(list_items.getPayment_type().equals("Subscription"))
               holder.shopinti.setText("Gastos");
            else
                holder.shopinti.setText(ShopName);

        String status = list_items.getStatus();

        Log.e("STATUS", status);

        if(status.equals("Failed")){
            holder.shopinti.setTextColor(Color.parseColor("#cf142b"));  //This!!!

        }else if(status.equals("Success")){
            holder.shopinti.setTextColor(Color.parseColor("#237f52"));

        }else{
            holder.shopinti.setTextColor(Color.parseColor("#FFCC00"));
        }




        }catch (Exception e){
           Log.e("TAG", "onBindViewHolder: "+e .getMessage()) ;
        }

//        holder.user_name.setText(list_items.getName());
//        holder.content.setText(list_items.getContent());
//        holder.time.setText(list_items.getTime());
    }

    //Setting the arraylist
    public void setListContent(ArrayList<Trans_history> list_members){
        this.list_members=list_members;
        notifyItemRangeChanged(0,list_members.size());

    }


    @Override
    public int getItemCount() {
        return list_members.size();
    }


    //View holder class, where all view components are defined
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView user_name,content,time;
        ImageView imageView;
        TextView shopinti;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.circleImageView3);
//            user_name=(TextView)itemView.findViewById(R.id.user_name);
//            content=(TextView)itemView.findViewById(R.id.content);
//            time=(TextView)itemView.findViewById(R.id.time);

            shopinti = itemView.findViewById(R.id.textView61);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public void removeAt(int position) {
        list_members.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, list_members.size());
    }


    public static String inPlaceReverse(final String input) {
        final StringBuilder builder = new StringBuilder(input);
        int length = builder.length();
        for (int i = 0; i < length / 2; i++) {
            final char current = builder.charAt(i);
            final int otherEnd = length - i - 1;
            builder.setCharAt(i, builder.charAt(otherEnd)); // swap
            builder.setCharAt(otherEnd, current);
        }

        return builder.toString();
    }
}