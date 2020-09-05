package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ContactUsShop extends BaseActivity1 {

    private MenuItem item;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_contact_us_shop);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Contact Us");

        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(6);
        item.setEnabled(false);

    }
}