package com.example.medconnect;

import android.app.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Utils {
//    public void hideKeyboard(View view, Activity activity){
//        try{
//            InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }

//    public void sendNotification(String title,String text){
//        NotificationCompat.Builder notification;
//        int uniqueID=40111;
//
//        notification = new NotificationCompat.Builder(this);
//        notification.setAutoCancel(true);
//
//        notification.setSmallIcon(R.drawable.ic_launcher_background);
//        notification.setTicker("this is a ticker");
//        notification.setWhen(System.currentTimeMillis());
//        notification.setContentTitle(title);
//        notification.setContentText(text);
//
//
//        Intent intent = new Intent(this, CustomerHomePage.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.setContentIntent(pendingIntent);
//
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        nm.notify(uniqueID,notification.build());
//    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void autoHideKeyboard(View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                autoHideKeyboard(innerView,activity);
            }
        }
    }
}