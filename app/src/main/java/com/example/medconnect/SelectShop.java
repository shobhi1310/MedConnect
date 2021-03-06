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

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import java.util.Locale;
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
    private FusedLocationProviderClient mFusedLocationClient;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private Double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d("New-LocationResult", "Here-2");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Log.d("New", wayLatitude.toString());
                        APICall(id,wayLatitude.toString(),wayLongitude.toString());
                    }
                    if (mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };

        queue = Volley.newRequestQueue(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        createExampleList();
    }

    public void createExampleList() {
        shops = new ArrayList<SelectShopCard>();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }else {
            fetchLocation();
        }
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
                                Log.d("distances",jsonObject.getString("travelDistance"));
                                String travelDistance="";
                                if(!jsonObject.getString("travelDistance").equals("-1")){
                                    travelDistance=jsonObject.getString("travelDistance")+"km";
                                }
                                filteredList.add(new SelectShopCard(jsonObject.getString("_id"), jsonObject.getString("name"), jsonObject.getString("address"), jsonObject.getString("phone"), travelDistance, coordinates.getDouble(0), coordinates.getDouble(1)));


                            }
                        } catch (JSONException e) {
                            Toast.makeText(SelectShop.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        shops = filteredList;
                        Log.d("Array Shop", shops.toString());
                        spinner.setVisibility(View.GONE);
                        LinearLayout t = findViewById(R.id.selectShopPrompt);
                        if (shops.size() > 0) {
                            t.setVisibility(View.GONE);
                        } else {
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Fetch", "here-2");
            //permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(SelectShop.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(SelectShop.this)
                        .setTitle("Requires location permission")
                        .setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        1000);
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
                ActivityCompat.requestPermissions(SelectShop.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this,

                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            // Log.d("New", location.toString());
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                Log.d("New-Normal", wayLatitude.toString());
                                APICall(id, wayLatitude.toString(), wayLongitude.toString());
                            } else {
                                if (ActivityCompat.checkSelfPermission(SelectShop.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        }
                    });


        }


//        Log.d("Fetch","New-1");
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
//        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(this,

                            new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        wayLatitude = location.getLatitude();
                                        wayLongitude = location.getLongitude();
                                        Log.d("New", wayLatitude.toString());
                                        APICall(id,wayLatitude.toString(),wayLongitude.toString());
                                    } else {
                                        if (ActivityCompat.checkSelfPermission(SelectShop.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SelectShop.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        }
                    });


                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);


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

    @Override
    protected void onResume() {
        super.onResume();
        fetchLocation();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
