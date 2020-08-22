package com.example.medconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GetStartedActivity extends AppCompatActivity {

    ImageView introImage;
    TextView title;
    TextView description;
    Button getStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_get_started);

        getSupportActionBar().hide();

        introImage = findViewById(R.id.intro_img4);
        title = findViewById(R.id.intro_title4);
        description = findViewById(R.id.intro_description4);
        getStarted = findViewById(R.id.get_started);

        title.setText("Let's Start!");
        description.setText("Continuing you are agreeing to the terms of use and privacy policy.");
        introImage.setImageResource(R.drawable.img4);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                boolean isExistingMember = intent.getBooleanExtra("Login", false);
                if(isExistingMember == true) {
                    Toast.makeText(getApplicationContext(), "Welcome Back to MedConnect!", Toast.LENGTH_LONG);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Welcome to MedConnect!", Toast.LENGTH_LONG);
                }
//                Intent intent = new Intent(getActivity(), );
//                startActivity(intent);
            }
        });

    }
}
