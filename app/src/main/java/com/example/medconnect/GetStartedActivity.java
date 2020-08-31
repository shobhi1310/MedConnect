package com.example.medconnect;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.example.medconnect.GetUserLocation.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION;

public class GetStartedActivity extends AppCompatActivity {

    ImageView introImage;
    TextView title;
    TextView description;
    Button getStarted;
    private FusedLocationProviderClient fusedLocationClient;
    public static final String Data = "StoredData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_get_started);

//        getSupportActionBar().hide();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
//                boolean isExistingMember = intent.getBooleanExtra("Login", false);
                boolean isCustomer = intent.getBooleanExtra("customer",false);
                if(isCustomer == true) {
                    Toast.makeText(getApplicationContext(), "Welcome Back to MedConnect!", Toast.LENGTH_LONG);
                    Intent intent_1 = new Intent(GetStartedActivity.this, CustomerHomePage.class);
                    startActivity(intent_1);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Welcome to MedConnect!", Toast.LENGTH_LONG);
                    Intent intent_1 = new Intent(GetStartedActivity.this, ShopOwnerHome.class);
                    startActivity(intent_1);
                }
            }
        });
        fetchLocation();
    }
    private void fetchLocation(){
        if (ContextCompat.checkSelfPermission(
                GetStartedActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            //permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(GetStartedActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //explain user and try again to grant permission
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                //showInContextUI(...);
                new AlertDialog.Builder(this)
                        .setTitle("Requires location permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(GetStartedActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
            else {
                //asking for the first time
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                ActivityCompat.requestPermissions(GetStartedActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
            }
        }
        else{
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double latitude=location.getLatitude();
                                Double longitude=location.getLongitude();
                                saveLocation(latitude, longitude);
                                Toast.makeText(getApplicationContext(),"Latitude and Longitude"+latitude.toString()+" "+longitude.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION) {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //after getting this information run your code here
            }
        }
    }

    public void saveLocation(Double latitude, Double longitude) {
        SharedPreferences sharedPreferences = getSharedPreferences(Data, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("LATITUDE", latitude.toString());
        editor.putString("LONGITUDE", longitude.toString());

        editor.apply();
    }
}
