package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopKeeperProfile extends BaseActivity1 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_shop_keeper_profile);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Your Profile");

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