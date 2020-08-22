package com.example.medconnect;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class medicineAdapter extends RecyclerView.Adapter<medicineAdapter.medicineHolder> {
    private ArrayList<MedicineItem> mList;



    public static class medicineHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView text1;
        public TextView text2;

        public medicineHolder(@NonNull View itemView) {
            super(itemView);

            this.mImageView = itemView.findViewById(R.id.imageView1);
            this.text1 = itemView.findViewById(R.id.textView1);
            this.text2 = itemView.findViewById(R.id.textView2);

        }
    }

    public medicineAdapter(ArrayList<MedicineItem> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public medicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here bug can occur
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        medicineHolder evh = new medicineHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull medicineHolder holder, int position) {
        MedicineItem currItem = this.mList.get(position);

        holder.mImageView.setImageResource(currItem.getImageR());

        holder.text1.setText(currItem.getText1());
        holder.text2.setText(currItem.gettext2());

    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

//    @Override
    public void filterList(ArrayList<MedicineItem> filteredlist){
        this.mList = filteredlist;
        notifyDataSetChanged();
    }

}
