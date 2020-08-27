package com.example.medconnect;

import java.io.Serializable;

public class SelectShopCard implements Serializable {


    private String shopName;
    private String shopAddress;
    private String shopMobile;
    private String distance;
    private String id;
    private double latitude;
    private double longitude;




    public  SelectShopCard(String id,String shopName,String shopAddress, String shopMobile,String distance,double latitude,double longitude){

        this.shopName=shopName;
        this.shopAddress=shopAddress;
        this.shopMobile=shopMobile;
        this.distance=distance;
        this.id=id;
        this.latitude=latitude;
        this.longitude=longitude;




    }

    public String getShopMobile() {
        return shopMobile;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDistance() {
        return distance;
    }
}
