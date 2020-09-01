package com.example.medconnect;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DistanceCalculator {
    private RequestQueue queue;
    String origin_lat;
    String origin_long;
    public static final String Data = "StoredData";
    private List<JSONObject> sortedShopList;
    private Context context;

    public List<JSONObject> getSortedShopList() {
        return sortedShopList;
    }

    public DistanceCalculator(Context c, String latitude, String longitude){
        // initialize lat and long
        context=c;
        queue = Volley.newRequestQueue(c);
        origin_lat = latitude;
        origin_long = longitude;

        Log.d("Constructor","Here");
        APIAllShopsCall();
    }
    private void APIAllShopsCall(){
        Log.d("APIAllShopsCall","Here");
        String url = "https://glacial-caverns-39108.herokuapp.com/shop/city";
        JSONArray shopList;
        queue.cancelAll("AllShops");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray shopList = new JSONObject(response).getJSONArray("shops");
                            Log.d("ShopList",shopList.toString());
                            APICall(shopList);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Log.e("AllShopsCall", String.valueOf(error));
                    }
                });
        stringRequest.setTag("AllShops");
        queue.add(stringRequest);
    }
    private void APICall(final JSONArray shopList){
        String destinations = "";
        queue.cancelAll("SortedShops");
        Log.d("APICall","Here");
        for(int i=0;i<shopList.length();i++){
            try {
                JSONObject o = shopList.getJSONObject(i);
                JSONArray location = o.getJSONArray("location");
                destinations += Double.toString(location.getDouble(0));
                destinations += ",";
                destinations += Double.toString(location.getDouble(1));
                destinations += ";";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        destinations = destinations.substring(0,destinations.length()-1);

        String base_url = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?";
        String key = "Au9zVKAUprgIuzokDYXWlvT_4ReDptHhq5U1nK-dpEb5sSyocJV869S5aoplspkI";
        String travelMode = "walking";
        String origins = origin_lat+","+origin_long;
        String url = base_url + "&key=" + key + "&origins=" + origins +"&destinations="+ destinations + "&travelMode=" + travelMode;
//        queue.cancelAll("CurrentBookings");
        Log.d("Test_url",url);
        final List<JSONObject> list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        ArrayList<CustomerBookingHistoryCard> sortedShop = new ArrayList<>();
                        try {
                            JSONArray resourceSets = new JSONObject(response).getJSONArray("resourceSets");
                            JSONArray resources = resourceSets.getJSONObject(0).getJSONArray("resources");
                            JSONArray results = resources.getJSONObject(0).getJSONArray("results");
                            for(int i = 0; i < results.length(); i++) {
                                JSONObject j = results.getJSONObject(i);
                                JSONObject s = shopList.getJSONObject(i);
                                String id = s.getString("_id");
                                j.put("_id",id);
                                list.add(j);
                            }
                            Collections.sort(list, new Comparator<JSONObject>() {

                                private static final String KEY_NAME = "travelDistance";
                                @Override
                                public int compare(JSONObject a, JSONObject b) {
                                    double d1 = 0;
                                    double d2 = 0;
                                    try {
                                        d1 = a.getDouble(KEY_NAME);
                                        d2 = b.getDouble(KEY_NAME);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(d1<d2){
                                        return -1;
                                    }else if(d1==d2){
                                        return 0;
                                    }
                                    return 1;
                                }

                            });
                        sortedShopList = list;
                        Log.d("SortedShopList",sortedShopList.toString());
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Data, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.d("Save Sorted Shops","Here");

                            editor.putString("SHOPLIST", list.toString());
                            editor.apply();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Log.e("sortingAPIcall", String.valueOf(error));
                    }
                });
        stringRequest.setTag("SortedShops");
        queue.add(stringRequest);
    }

}
