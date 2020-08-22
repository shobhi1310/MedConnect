package com.example.medconnect;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopOwnerHomeAdapter extends RecyclerView.Adapter<ShopOwnerHomeAdapter.ShopOwnerHomeViewHolder> {

    private ArrayList<ShopOwnerHomeCard> medicineList;
    private  OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void updateStatus(int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }







    public static class ShopOwnerHomeViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView status;
        public TextView remove;
        public TextView update;



        public ShopOwnerHomeViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            status = itemView.findViewById(R.id.status);
            remove=itemView.findViewById(R.id.remove);
            update = itemView.findViewById(R.id.update);


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

    public  ShopOwnerHomeAdapter(ArrayList<ShopOwnerHomeCard> medicines){
        medicineList=medicines;
    }

    @NonNull
    @Override
    public ShopOwnerHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_owner_home_card,parent,false);
        ShopOwnerHomeViewHolder svh= new ShopOwnerHomeViewHolder(v,mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerHomeViewHolder holder, int position) {
        ShopOwnerHomeCard card=  medicineList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        if(card.getStatus()){
            holder.status.setText("available");
            holder.status.setTextColor(Color.parseColor("#0A710E"));
        }
        else{
            holder.status.setText("unavailable");
            holder.status.setTextColor(Color.parseColor("#FFFF1744"));
        }




    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }




}
