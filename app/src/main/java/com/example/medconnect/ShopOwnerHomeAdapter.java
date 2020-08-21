package com.example.medconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopOwnerHomeAdapter extends RecyclerView.Adapter<ShopOwnerHomeAdapter.ShopOwnerHomeViewHolder> {

    private ArrayList<ShopOwnerHomeCard> medicineList;
    public static class ShopOwnerHomeViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView status;

        public ShopOwnerHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            status = itemView.findViewById(R.id.status);
        }
    }

    public  ShopOwnerHomeAdapter(ArrayList<ShopOwnerHomeCard> medicines){
        medicineList=medicines;
    }

    @NonNull
    @Override
    public ShopOwnerHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_owner_home_card,parent,false);
        ShopOwnerHomeViewHolder svh= new ShopOwnerHomeViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerHomeViewHolder holder, int position) {
        ShopOwnerHomeCard card=  medicineList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        holder.status.setText(card.getStatus());


    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }
}
