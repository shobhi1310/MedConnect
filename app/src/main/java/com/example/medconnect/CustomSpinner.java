package com.example.medconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.example.medconnect.ui.main.ShopOwnerFragment;

public class CustomSpinner {
    Activity activity;
    AlertDialog dialog;


    public CustomSpinner(Activity myActivity){
        activity=myActivity;
    }

//    public CustomSpinner(ShopOwnerFragment shopOwnerFragment) {
//        this.shopOwnerFragment=shopOwnerFragment;
//    }

    public void startSpinner(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);


        LayoutInflater inflater= activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_spinner,null));
        builder.setCancelable(true);

        dialog=builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void dismissSpinner(){
        dialog.dismiss();
    }
}
