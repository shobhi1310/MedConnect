package com.example.medconnect;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopOwnerBookingHistoryAdapter extends RecyclerView.Adapter<ShopOwnerBookingHistoryAdapter.ShopOwnerBookingHistoryViewHolder> {

    private ArrayList<ShopOwnerBookingHistoryCard> ordersList;











    public static class ShopOwnerBookingHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView customerName;
        public TextView customerMobile;
        public TextView bookingDate;



        public ShopOwnerBookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            customerName = itemView.findViewById(R.id.customerName);
            customerMobile = itemView.findViewById(R.id.customerMobile);
            bookingDate = itemView.findViewById(R.id.bookingDate);

        }
    }

    public  ShopOwnerBookingHistoryAdapter(ArrayList<ShopOwnerBookingHistoryCard> orders){
        ordersList=orders;
    }

    @NonNull
    @Override
    public ShopOwnerBookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shopowner_booking_history_card,parent,false);
        ShopOwnerBookingHistoryViewHolder svh= new ShopOwnerBookingHistoryViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerBookingHistoryViewHolder holder, int position) {
        ShopOwnerBookingHistoryCard card=  ordersList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        holder.customerName.setText(card.getCustomerName());
        holder.customerMobile.setText(card.getCustomerMobile());
        holder.bookingDate.setText(card.getBookingDate());


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }




}
