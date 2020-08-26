package com.example.medconnect;

import java.io.Serializable;

public class SelectShopCard implements Serializable {


    private String shopName;
    private String shopAddress;
    private String shopMobile;
    private String distance;
    private String id;




    public  SelectShopCard(String id,String shopName,String shopAddress, String shopMobile,String distance){

        this.shopName=shopName;
        this.shopAddress=shopAddress;
        this.shopMobile=shopMobile;
        this.distance=distance;
        this.id=id;




    }

    public String getShopMobile() {
        return shopMobile;
    }

    public String getId() {
        return id;
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
