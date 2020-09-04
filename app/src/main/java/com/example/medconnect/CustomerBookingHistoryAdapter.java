package com.example.medconnect;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerBookingHistoryAdapter extends RecyclerView.Adapter<CustomerBookingHistoryAdapter.CustomerBookingHistoryViewHolder> {

    private ArrayList<CustomerBookingHistoryCard> ordersList;

    public static class CustomerBookingHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView shopName;
        public TextView shopAddress;
        public TextView shopMobile;
        public TextView bookingDate;
        public Button  locateMap;



        public CustomerBookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            shopName = itemView.findViewById(R.id.shopName);
            shopAddress = itemView.findViewById(R.id.shopAddress);
            shopMobile = itemView.findViewById(R.id.shopMobile);
            bookingDate=itemView.findViewById(R.id.bookingDate);
            locateMap = itemView.findViewById(R.id.locateMap);

        }
    }

    public  CustomerBookingHistoryAdapter(ArrayList<CustomerBookingHistoryCard> orders){
        ordersList=orders;
    }

    @NonNull
    @Override
    public CustomerBookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_booking_history_card,parent,false);

        CustomerBookingHistoryViewHolder svh= new CustomerBookingHistoryViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerBookingHistoryViewHolder holder, int position) {
        CustomerBookingHistoryCard card=  ordersList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        holder.shopName.setText(card.getShopName());
        holder.shopAddress.setText(card.getShopAddress());
        holder.shopMobile.setText(card.getShopMobile());
        holder.bookingDate.setText(card.getBookingDate());


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }




}
