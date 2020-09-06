package com.example.medconnect;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopOwnerCurrentBookingsAdapter extends RecyclerView.Adapter<ShopOwnerCurrentBookingsAdapter.ShopOwnerCurrentBookingsViewHolder> {

    private ArrayList<ShopOwnerCurrentBookingsCard> ordersList;

    public static class ShopOwnerCurrentBookingsViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView customerName;
        public TextView customerMobile;
        public TextView bookingDate;
        public TextView deadline;



        public ShopOwnerCurrentBookingsViewHolder(@NonNull View itemView) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            customerName = itemView.findViewById(R.id.customerName);
            customerMobile = itemView.findViewById(R.id.customerMobile);
            bookingDate = itemView.findViewById(R.id.bookingDate);
            deadline=itemView.findViewById(R.id.deadline);

        }
    }

    public  ShopOwnerCurrentBookingsAdapter(ArrayList<ShopOwnerCurrentBookingsCard> orders){
        ordersList=orders;
    }

    @NonNull
    @Override
    public ShopOwnerCurrentBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shopowner_current_bookings_card,parent,false);
        ShopOwnerCurrentBookingsViewHolder svh= new ShopOwnerCurrentBookingsViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerCurrentBookingsViewHolder holder, int position) {
        ShopOwnerCurrentBookingsCard card=  ordersList.get((position));
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        holder.customerName.setText(card.getCustomerName());
        holder.customerMobile.setText(card.getCustomerMobile());
        holder.bookingDate.setText(card.getBookingDate());
        holder.deadline.setText(card.getDeadline());


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }




}
