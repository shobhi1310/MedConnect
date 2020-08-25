package com.example.medconnect;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShopOwnerSearchMedicineAdapter extends RecyclerView.Adapter<ShopOwnerSearchMedicineAdapter.ShopOwnerSearchMedicineHolder> implements Filterable {
    private ArrayList<ShopOwnerSearchMedicineCard> mList;
    private ArrayList<ShopOwnerSearchMedicineCard> copymList;
    private OnItemCLickListener mListener;

    public ShopOwnerSearchMedicineAdapter(ArrayList<ShopOwnerSearchMedicineCard> list){
        this.mList = list;
        //here we are doing deep copy instead of shallow copy
        this.copymList = new ArrayList<>(list);
    }

    @Override
    public Filter getFilter() {
        return smartFilter;
    }

    private Filter smartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ShopOwnerSearchMedicineCard> filteredList = new ArrayList<>();

            if(constraint==null || constraint.length()==0){
                filteredList.addAll(copymList);
            }else{
                String filteredString = constraint.toString().toLowerCase().trim();

                for(ShopOwnerSearchMedicineCard medItem:copymList){
                    if(medItem.getMedicineName().toString().toLowerCase().contains(filteredString)){
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
            mList.clear();
            mList.addAll((ArrayList)(results.values));
            notifyDataSetChanged();
        }
    };


    public interface OnItemCLickListener{
        void onItemClick(int position);
    }


    public void setOnItemCLickListener(OnItemCLickListener lis){
        this.mListener=lis;
    }


    public static class ShopOwnerSearchMedicineHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView medicineName;
        public TextView manufacturer;
        public TextView status;
        public TextView weight;

        public ShopOwnerSearchMedicineHolder(@NonNull View itemView, final OnItemCLickListener lis) {
            super(itemView);

//            this.mImageView = itemView.findViewById(R.id.imageView1);
            this.medicineName = itemView.findViewById(R.id.medicine);
            this.manufacturer = itemView.findViewById(R.id.manufacturer);
            this.status = itemView.findViewById(R.id.status);
            this.weight = itemView.findViewById(R.id.strength);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(lis!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            lis.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }



    @NonNull
    @Override
    public ShopOwnerSearchMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here bug can occur
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        ShopOwnerSearchMedicineHolder evh = new ShopOwnerSearchMedicineHolder(v,this.mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOwnerSearchMedicineHolder holder, int position) {
        ShopOwnerSearchMedicineCard currItem = this.mList.get(position);

//        holder.mImageView.setImageResource(currItem.getImageR());

        holder.medicineName.setText(currItem.getMedicineName());
        holder.manufacturer.setText(currItem.getManufacturer());
//        holder.status.setB(currItem.getStatus());
//        if(currItem.getStatus()==true){
//            holder.status.setText("Available");
//        }else{
//            holder.status.setText("Unavailable");
//        }
        holder.weight.setText(currItem.getWeight());

    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    //    @Override
    public void filterList(ArrayList<ShopOwnerSearchMedicineCard> filteredlist){
        this.mList = filteredlist;
        notifyDataSetChanged();
    }

}
