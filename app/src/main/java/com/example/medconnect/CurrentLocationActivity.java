package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.example.medconnect.GetUserLocation.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION;

public class CurrentLocationActivity extends BaseActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;
    public static final String Data = "StoredData";
    private GoogleMap mMap;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_current_location);
//        setContentView(R.layout.activity_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.currentmap);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Current Location");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fetchLocation();
    }

    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(
                CurrentLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            //permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(CurrentLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Requires location permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(CurrentLocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
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
            } else {
                ActivityCompat.requestPermissions(CurrentLocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
            }
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            saveLocation(latitude, longitude);
                            Toast.makeText(getApplicationContext(), "Latitude and Longitude" + latitude.toString() + " " + longitude.toString(), Toast.LENGTH_SHORT).show();
                            DistanceCalculator dc = new DistanceCalculator(CurrentLocationActivity.this, latitude.toString(), longitude.toString());
                            LatLng current = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                            mMap.setMinZoomPreference(10);
                        }
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //after getting this information run your code here
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    saveLocation(latitude, longitude);
                                    Toast.makeText(getApplicationContext(), "Latitude and Longitude" + latitude.toString() + " " + longitude.toString(), Toast.LENGTH_SHORT).show();
                                    DistanceCalculator dc = new DistanceCalculator(CurrentLocationActivity.this, latitude.toString(), longitude.toString());
                                    LatLng current = new LatLng(latitude, longitude);
                                    mMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                                    mMap.setMinZoomPreference(10);
                                }
                            }
                        });
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
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}