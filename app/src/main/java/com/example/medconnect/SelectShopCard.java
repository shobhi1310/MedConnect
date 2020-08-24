package com.example.medconnect;

public class SelectShopCard {


    private String shopName;
    private String shopAddress;
    private String shopMobile;
    private String distance;



    public  SelectShopCard(String shopName,String shopAddress, String shopMobile,String distance){

        this.shopName=shopName;
        this.shopAddress=shopAddress;
        this.shopMobile=shopMobile;
        this.distance=distance;




    }

    public String getShopMobile() {
        return shopMobile;
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
