package com.example.medconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.medconnect.GetUserLocation.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION;


public class SelectShop extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SelectShopAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<SelectShopCard> shops;
    private RequestQueue queue;
    private ProgressBar spinner;
    private String id;
    private FusedLocationProviderClient fusedLocationClient;
    public static final String Data = "StoredData";

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        spinner = findViewById(R.id.progress_loader);
        spinner.setVisibility(View.VISIBLE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Fetch", "New-2");
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                APICall(id, latitude.toString(), longitude.toString());
                Toast.makeText(SelectShop.this, location.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                buildAlertMessageNoGps();
            }
        };



        Log.d("Permission",Integer.toString(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)));
        Log.d("Permission",Integer.toString(PackageManager.PERMISSION_GRANTED));

        if (this.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION")==PackageManager.PERMISSION_GRANTED) {
            Log.d("Permission",Integer.toString(ContextCompat.checkSelfPermission(this,"android.permission.ACCESS_FINE_LOCATION")));
            Log.d("Permission",Integer.toString(PackageManager.PERMISSION_GRANTED));
//            if (ContextCompat.checkSelfPermission(this,"android.permission.ACCESS_FINE_LOCATION") ==
//                    PackageManager.PERMISSION_GRANTED ) {
//                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
//            } else {
//                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
//            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);



        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(SelectShop.this)
                        .setTitle("Requires location permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
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
                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            }


}


            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);

        // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        if(checkLocationPermission()) {
//
//        }

        queue = Volley.newRequestQueue(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public void createExampleList() {
        shops = new ArrayList<SelectShopCard>();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.selectShopRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new SelectShopAdapter(shops);

        mRecyclerView.setLayoutManager(mLayout);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SelectShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SelectShop.this, MedicineDetails.class);
//                intent.putExtra("ShopName",);
                intent.putExtra("Shop", shops.get(position));
                MedicineItem medicineItem = null;

                if (getIntent().getExtras() != null) {
                    medicineItem = (MedicineItem) getIntent().getSerializableExtra("Medicine");
                } else {
                    Log.d("Shop", "Not Found");
                }
                intent.putExtra("Medicine", medicineItem);
                startActivity(intent);
            }
        });

    }


    private void APICall(String id, final String latitude, final String longitude) {

        String url = "https://glacial-caverns-39108.herokuapp.com/medicine/shoplist/" + id;
//        String url="http://localhost:5000/medicine/shoplist/"+id;
        Log.d("URL", latitude);
        Log.d("URL", longitude);
        Log.d("URL", url);

        queue.cancelAll("ShopList");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        ArrayList<SelectShopCard> filteredList = new ArrayList<>();
                        try {
                            Log.d("shops result", response);
                            JSONArray result = new JSONObject(response).getJSONArray("shops");
                            //JSONArray tally = extractor();
//                                    .getJSONObject("list");
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");
                            //this.medicineList.add(new ShopOwnerSearchMedicineCard("Paracetamol","XYZ","150MG"));

                            for (int j = 0; j < result.length(); j++) {
                                JSONObject jsonObject = result.getJSONObject(j);

                                JSONArray coordinates = jsonObject.getJSONArray("location");

                                filteredList.add(new SelectShopCard(jsonObject.getString("_id"), jsonObject.getString("name"), jsonObject.getString("address"), jsonObject.getString("phone"), jsonObject.getString("travelDistance") + "km", coordinates.getDouble(0), coordinates.getDouble(1)));


                            }
                        } catch (JSONException e) {
                            Toast.makeText(SelectShop.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        shops = filteredList;
                        Log.d("Array Shop", shops.toString());
                        spinner.setVisibility(View.GONE);
                        LinearLayout t=findViewById(R.id.selectShopPrompt);
                        if(shops.size()>0){
                            t.setVisibility(View.GONE);
                        }
                        else{
                            t.setVisibility(View.VISIBLE);
                        }
                        buildRecyclerView();

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(SelectShop.this, "Server is not responding", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("latitude", latitude);
                params.put("longitude", longitude);
                return params;
            }
        };
        ;

        stringRequest.setTag("ShopList");
        // executing the request (adding to queue)
        queue.add(stringRequest);


    }

    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(
                SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("Fetch", "here-2");
            //permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(SelectShop.this)
                        .setTitle("Requires location permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
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
                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
            }
        }


//        Log.d("Fetch","New-1");
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
//        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);

    }

//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

//        if (requestCode == MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION  || requestCode==) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //after getting this information run your code here
//                if (ActivityCompat.checkSelfPermission(SelectShop.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//
//                Log.d("Permission","New-1");
//                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
//                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);
//
//            }
//        }
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }else {
//                        if (ContextCompat.checkSelfPermission(this,"android.permission.ACCESS_FINE_LOCATION") ==
//                                PackageManager.PERMISSION_GRANTED ) {
//                            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
//                        } else {
//                            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
//                        }
                        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

                        return;
                    }
                }
        }
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }



}
