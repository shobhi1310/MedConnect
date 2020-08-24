package com.example.medconnect;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SelectShopAdapter extends RecyclerView.Adapter<SelectShopAdapter.SelectShopViewHolder> {

    private ArrayList<SelectShopCard> shopList;
    private SelectShopAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public  void setOnItemClickListener(SelectShopAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class SelectShopViewHolder extends RecyclerView.ViewHolder{

        public TextView shopName;
        public TextView shopAddress;
        public TextView shopMobile;
        public TextView distance;



        public SelectShopViewHolder(@NonNull View itemView,final SelectShopAdapter.OnItemClickListener listener) {
            super(itemView);


            shopName = itemView.findViewById(R.id.shopName);
            shopAddress = itemView.findViewById(R.id.shopAddress);
            shopMobile = itemView.findViewById(R.id.shopMobile);
            distance=itemView.findViewById(R.id.shopDistance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }

    public  SelectShopAdapter(ArrayList<SelectShopCard> orders){
        shopList=orders;
    }

    @NonNull
    @Override
    public SelectShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card,parent,false);
        SelectShopViewHolder svh= new SelectShopViewHolder(v,mListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectShopViewHolder holder, int position) {
        SelectShopCard card=  shopList.get((position));

        holder.shopName.setText(card.getShopName());
        holder.shopAddress.setText(card.getShopAddress());
        holder.shopMobile.setText(card.getShopMobile());
        holder.distance.setText(card.getDistance());

//        CardView cardView=holder.shopCard;


    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }




}
