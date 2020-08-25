package com.example.medconnect;

import android.os.Bundle;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GoogleMapPage extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Map");


    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        map=googleMap;
        LatLng home= new LatLng(17.402203, 78.403849
        );
        LatLng hyderabad=new LatLng(17.374968, 78.485250);
        map.addMarker(new MarkerOptions().position(hyderabad).title("Hyderabad"));
        map.addMarker(new MarkerOptions().position(home).title("Home"));
        map.moveCamera(CameraUpdateFactory.newLatLng(hyderabad));
        map.setMinZoomPreference(10);

    }
}