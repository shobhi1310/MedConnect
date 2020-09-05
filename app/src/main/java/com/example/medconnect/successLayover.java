package com.example.medconnect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import static android.app.PendingIntent.getActivities;

public class successLayover extends AppCompatActivity {

    Button homePageRedirect;
    CheckBox notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_layover);

        TextView bookedMedicineName = findViewById(R.id.bookedMedicineDetails);
        TextView bookedMedicineFromShopName = findViewById(R.id.bookedFromShopName);
        TextView bookedMedicineFromShopAddress = findViewById(R.id.bookedFromShopAddress);
        notification = findViewById(R.id.notificationCheck);
        homePageRedirect = findViewById(R.id.homeRedirect);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            bookedMedicineName.setText((CharSequence) extras.get("medicine_name"));
            bookedMedicineFromShopName.setText((CharSequence) extras.get("shop_name"));
            bookedMedicineFromShopAddress.setText((CharSequence) extras.get("shop_address"));
        }

        homePageRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notification.isChecked()) {
                    notificationDialog("Booking Successful", "See your booking details...");
                }
                Intent i = new Intent(successLayover.this,CustomerHomePage.class);
                startActivity(i);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialog(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Tutorialspoint")
                //.setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }

}