package com.example.medconnect;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopOwnerHomeAdapter extends RecyclerView.Adapter<ShopOwnerHomeAdapter.ShopOwnerHomeViewHolder> implements Filterable {

    private ArrayList<ShopOwnerHomeCard> medicineList;
    private ArrayList<ShopOwnerHomeCard> copyList;
    private OnItemClickListener mListener;


    public ShopOwnerHomeAdapter(ArrayList<ShopOwnerHomeCard> list) {
        this.medicineList = list;
        //here we are doing deep copy instead of shallow copy
        this.copyList = new ArrayList<>(list);
    }

    public void setOnItemCLickListener(ShopOwnerHomeAdapter.OnItemClickListener lis) {
        this.mListener = lis;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void updateStatus(int position);

        void onItemClick(int position);
    }


//    public  void setOnItemClickListener(OnItemClickListener listener){
//        mListener = listener;
////        this.copyList = new ArrayList<>();
//    }


    @Override
    public Filter getFilter() {
        return smartFilter;
    }

    private Filter smartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ShopOwnerHomeCard> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(copyList);
            } else {
                String filteredString = constraint.toString().toLowerCase().trim();

                for (ShopOwnerHomeCard medItem : copyList) {
                    if (medItem.getMedicine().toLowerCase().indexOf(filteredString)==0) {
                        Log.d("Filtering",medItem.getMedicine().toLowerCase()+medItem.getMedicine().toLowerCase().indexOf(filteredString));
                        filteredList.add(medItem);
                    }
                }

            }
            FilterResults endResult = new FilterResults();
            endResult.values = filteredList;
            return endResult;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            medicineList.clear();
            medicineList.addAll((ArrayList) (results.values));
            notifyDataSetChanged();
        }
    };


    public static class ShopOwnerHomeViewHolder extends RecyclerView.ViewHolder {
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView status;
        public TextView remove;
        public TextView update;


        public ShopOwnerHomeViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            status = itemView.findViewById(R.id.status);
            remove = itemView.findViewById(R.id.remove);
            update = itemView.findViewById(R.id.update);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onItemClick(pos);
                        }
                    }
                }
            });


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.updateStatus(position);

                        }
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public ShopOwnerHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_owner_home_card, parent, false);
        ShopOwnerHomeViewHolder svh = new ShopOwnerHomeViewHolder(v, mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerHomeViewHolder holder, int position) {
        ShopOwnerHomeCard card = medicineList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        if (card.getStatus()) {
            holder.status.setText("available");
            holder.status.setTextColor(Color.parseColor("#0A710E"));
        } else {
            holder.status.setText("unavailable");
            holder.status.setTextColor(Color.parseColor("#FFFF1744"));
        }


    }

    @Override
    public int getItemCount() {
        return this.medicineList.size();
    }

    //    @Override
    public void filterList(ArrayList<ShopOwnerHomeCard> filteredlist) {
        this.medicineList = filteredlist;
        notifyDataSetChanged();
    }


    //end of code


}
