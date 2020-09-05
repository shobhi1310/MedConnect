package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ShopKeeperProfile extends BaseActivity1 {

    public static final String Data = "StoredData";
    String name;
    String phone;
    String email;
    String address;
    private MenuItem item;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_shop_keeper_profile);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Your Profile");

        TextView Name = findViewById(R.id.shopName);
        TextView Email = findViewById(R.id.email);
        TextView Phone = findViewById(R.id.phone);
        TextView Address = findViewById(R.id.address);

        nav = findViewById(R.id.navigation);
        item = nav.getMenu().getItem(0);
        item.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        name = sharedPreferences.getString("NAME", "");
        email = sharedPreferences.getString("EMAIL", "");
        phone = sharedPreferences.getString("PHONE", "");
        address = sharedPreferences.getString("ADDRESS", "");

        Name.setText(name);
        Email.setText(email);
        Phone.setText(phone);
        Address.setText(address);

        Button editProfile = (Button)findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopKeeperProfile.this, ShopOwnerEditProfile.class);
                startActivity(intent);
            }
        });
    }
}