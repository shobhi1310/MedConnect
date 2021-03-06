package com.example.medconnect;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerBookingHistoryAdapter extends RecyclerView.Adapter<CustomerBookingHistoryAdapter.CustomerBookingHistoryViewHolder>  {

    private ArrayList<CustomerBookingHistoryCard> ordersList;

    private OnItemClickListener mListener;
    private CustomerBookingHistoryCard specialCard;

    public void setOnItemCLickListener(CustomerBookingHistoryAdapter.OnItemClickListener lis) {
        this.mListener = lis;
    }


    public interface OnItemClickListener {
        void onClickToLocate(int position);
    }


    public class CustomerBookingHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView medicine;
        public TextView strength;
        public TextView manufacturer;
        public TextView shopName;
        public TextView shopAddress;
        public TextView shopMobile;
        public TextView bookingDate;
        public TextView deadline;
        public TextView deadlineHeading;
        public TextView  locate;
        public TextView latitude;
        public TextView longitude;




        public CustomerBookingHistoryViewHolder(@NonNull View itemView,final CustomerBookingHistoryAdapter.OnItemClickListener listener) {
            super(itemView);

            medicine = itemView.findViewById(R.id.medicine);
            strength = itemView.findViewById(R.id.strength);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            shopName = itemView.findViewById(R.id.shopName);
            shopAddress = itemView.findViewById(R.id.shopAddress);
            shopMobile = itemView.findViewById(R.id.shopMobile);
            bookingDate=itemView.findViewById(R.id.bookingDate);
            locate = itemView.findViewById(R.id.locateMap);



            deadline=itemView.findViewById(R.id.deadline);
            deadlineHeading=itemView.findViewById(R.id.deadlineHeading);


//            locate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        listener.onClickToLocate(specialCard,(Button)locate);
//                    }
//                }
//            });
            locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickToLocate(position);
                        }
                    }
                }
            });
        }










    }

    public  CustomerBookingHistoryAdapter(ArrayList<CustomerBookingHistoryCard> orders){
        ordersList=orders;
    }

    @NonNull
    @Override
    public CustomerBookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_booking_history_card,parent,false);

        CustomerBookingHistoryViewHolder svh= new CustomerBookingHistoryViewHolder(v,mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerBookingHistoryViewHolder holder, int position) {
        CustomerBookingHistoryCard card=  ordersList.get((position));
        specialCard = card;
        holder.medicine.setText(card.getMedicine());
        holder.strength.setText(card.getStrength());
        holder.manufacturer.setText(card.getManufacturer());
        holder.shopName.setText(card.getShopName());
        holder.shopAddress.setText(card.getShopAddress());
        holder.shopMobile.setText(card.getShopMobile());
        holder.bookingDate.setText(card.getBookingDate());
        holder.deadline.setText(card.getDeadline());



        if(card.isExpired()) {
            holder.deadline.setVisibility(View.GONE);
            holder.deadlineHeading.setVisibility(View.GONE);
            holder.locate.setVisibility(View.GONE);
        }
//        }else{
//            holder.locateMap.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }




}
