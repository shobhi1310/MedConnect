package com.example.medconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustomSpinner {
    Activity activity;
    AlertDialog dialog;

    CustomSpinner(Activity myActivity){
        activity=myActivity;
    }

    void startSpinner(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);

        LayoutInflater inflater= activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_spinner,null));
        builder.setCancelable(true);

        dialog=builder.create();
        dialog.show();

    }

    void dismissSpinner(){
        dialog.dismiss();
    }
}
