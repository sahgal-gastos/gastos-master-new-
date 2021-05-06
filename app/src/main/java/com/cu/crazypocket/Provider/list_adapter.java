package com.cu.crazypocket.Provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cu.crazypocket.Data.Provider_data;
import com.cu.crazypocket.R;

import java.util.ArrayList;

class list_adapter extends  ArrayAdapter<Provider_data> {

    ArrayList<Provider_data>mFilteredList,mArrayList;
    Context sContext;
    TextView PaymentText;

    public list_adapter(Context context, ArrayList<Provider_data> objects) {
        super(context, R.layout.list_row, R.id.shop_name, objects);
        this.sContext =  context;
         this.mFilteredList =objects;
        this.mArrayList = objects;

    }

    public ArrayList<Provider_data> getmFilteredList() {
        return mFilteredList;
    }

    @Override
    public int getCount() {
        return mFilteredList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);

    }


    @SuppressLint("NewApi")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (parent == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, null);
            else
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        try {
            Provider_data customer = mFilteredList.get(position);
            TextView tittle = convertView.findViewById(R.id.shop_name);
//            Log.e(TAG, "getView: " + customer.getShopname());
//            Log.e(TAG, "getView: " + customer.getAddress());
//            Log.e(TAG, "getView: " + customer.getPhoneno());
            tittle.setText(customer.getShopname());
            ((TextView) convertView.findViewById(R.id.shop_add)).setText(customer.getAddress());
            if(customer.getShoppic().length()>=10) {
                byte[] decodedString = Base64.decode(customer.getShoppic(), Base64.DEFAULT);
                Bitmap decodedbit = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ((ImageView) convertView.findViewById(R.id.img22)).setImageBitmap(decodedbit);
            }
            else{
                ((ImageView) convertView.findViewById(R.id.img22)).setImageDrawable(ContextCompat.getDrawable(sContext, R.drawable.shop));

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
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

                        if (androidVersion.getShopname().toLowerCase().contains(charString) ||
                                androidVersion.getPhoneno().toLowerCase().contains(charString) ||
                                androidVersion.getAddress().toLowerCase().contains(charString)
                            ||androidVersion.getShopname() .contains(charString) ||
                                androidVersion.getPhoneno() .contains(charString) ||
                                androidVersion.getAddress() .contains(charString)) {

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













//
//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
//
//    Filter myFilter = new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            Provider_data customer = (Provider_data) resultValue;
//            return customer.getShopname();
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (Provider_data cust : tempCustomer) {
//                    if (cust.getShopname().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(cust);
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            ArrayList<Provider_data> c =  (ArrayList<Provider_data> )results.values ;
//            if (results != null && results.count > 0) {
//                clear();
//                for (Provider_data cust : c) {
//                    add(cust);
//                    notifyDataSetChanged();
//                }
//            }
//            else{
//                clear();
//                notifyDataSetChanged();
//            }
//        }
//    };

//    ArrayList<Provider_data> filterList,orignallist,tempCustomer, suggestions;;
//    Context context;
//
//    public list_adapter( Context context , ArrayList<Provider_data> arrayList) {
//        super(context, R.layout.list_row, R.id.shop_name, arrayList);
//        this.filterList = arrayList;
//        this.orignallist = arrayList;
//        this.context = context;
//
//        this.tempCustomer = new ArrayList<Provider_data>(arrayList);
//        this.suggestions = new ArrayList<Provider_data>(arrayList);
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
//        return filterList.size();
//    }
//
//
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getView(int position, View convertView, final ViewGroup parent) {
//
//        Provider_data provider_data = filterList.get(position);
//        if (convertView == null) {
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            convertView = layoutInflater.inflate(R.layout.list_row, null);
//
//            final View v = convertView;
//
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    Intent intent = new Intent(context , PaymentClass.class);
////                    context.startActivity(intent);
//                    TextView payment_text = v.findViewById(R.id.paynow_option);
//                    if(payment_text.getVisibility() == View.VISIBLE){
//                        payment_text.setVisibility(View.GONE);
//                        Log.d(TAG , "onClick: ");
//                    }
//                    else{
//                        payment_text.setVisibility(View.VISIBLE);
//                    }
//
//                    payment_text.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(context , "PAY NOW" , Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                }
//            });
//            TextView tittle = convertView.findViewById(R.id.shop_name);
//            Log.e(TAG , "getView: " +provider_data.getShopname() );
//            tittle.setText(provider_data.getShopname());
//            ((TextView)convertView.findViewById(R.id.shop_add)).setText(provider_data.getAddress());
//        }
//        return convertView;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return filterList.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
////    @Override
////    public Filter getFilter()
////    {
////        return new    Filter() {
////        @Override
////        protected FilterResults performFiltering(CharSequence constraint) {
////            FilterResults filterResults = new FilterResults();
////            ArrayList<Provider_data> tempList=new ArrayList<Provider_data>();
////            //constraint is the result from text you want to filter against.
////            //objects is your data set you will filter from
////            if(constraint != null && orignallist!=null) {
////                int length=orignallist.size();
////                int i=0;
////                while(i<length){
////                    Provider_data  item=orignallist.get(i);
////
////                    if(item.getShopname().contains(constraint))
////                    tempList.add(item);
////
////                    i++;
////                }
////                //following two lines is very important
////                //as publish result can only take FilterResults objects
////                 filterResults.values = tempList;
////                filterResults.count = tempList.size();
////            }
////            return filterResults;
////        }
////
////        @SuppressWarnings("unchecked")
////        @Override
////        protected void publishResults(CharSequence contraint, FilterResults results) {
////             filterList = (ArrayList<Provider_data>) results.values;
////
////
////                notifyDataSetChanged();
////
////        }
////    };
////    }
//
//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
//    Filter myFilter =new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            Provider_data customer =(Provider_data) resultValue ;
//            return customer.getShopname();
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (Provider_data cust : tempCustomer) {
//                    if (cust.getShopname().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(cust);
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            ArrayList<Provider_data> c =  (ArrayList<Provider_data> )results.values ;
//            if (results != null && results.count > 0) {
//                clear();
//                for (Provider_data cust : c) {
//                    add(cust);
//                    notifyDataSetChanged();
//                }
//            }
//            else{
//                clear();
//                notifyDataSetChanged();
//            }
//        }
//    };


}