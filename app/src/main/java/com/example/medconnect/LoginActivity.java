package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.medconnect.ui.main.CustomerFragment;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    String Email;
    String Password;
    RadioButton selectedRadioBtn;
    RadioGroup custOrShop;
    String CustomerOrShopOwner;
//    boolean isLoggedOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.userLogin);

//        Intent i = getIntent();
//        isLoggedOut = i.getBooleanExtra("loggedOut", false);

        custOrShop = findViewById(R.id.customerOrShopOwner);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString();
                Password = password.getText().toString();
                int selectedId = custOrShop.getCheckedRadioButtonId();
                selectedRadioBtn = findViewById(selectedId);
                if(selectedId == -1) {
                    Toast.makeText(LoginActivity.this,"Select either Customer or Shop Owner", Toast.LENGTH_SHORT).show();
                } else {
                    CustomerOrShopOwner = selectedRadioBtn.getText().toString();
                }



                // Email and Password Verification -->

                // If verified -->
                Toast.makeText(getApplicationContext(), (Email + " " + Password), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, GetStartedActivity.class);
                intent.putExtra("Login", true);
                startActivity(intent);

            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        if(isLoggedOut) {
//            finishAffinity();
//        }
//    }
}