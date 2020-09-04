package com.example.medconnect;

import android.app.Activity;

import android.content.Context;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class Utils {
    public void hideKeyboard(View view, Activity activity){
        try{
            InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    /*
    public void sendNotification(String title,String text){
        NotificationCompat.Builder notification;
        int uniqueID=40111;

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setTicker("this is a ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(title);
        notification.setContentText(text);


        Intent intent = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = getActivities(this,0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
    */
}