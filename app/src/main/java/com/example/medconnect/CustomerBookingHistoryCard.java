package com.example.medconnect;

public class CustomerBookingHistoryCard {
    private String medicine;
    private String strength;
    private String manufacturer;
    private String shopName;
    private String shopAddress;
    private String shopMobile;



    public  CustomerBookingHistoryCard(String medicine,String strength,String manufacturer,String shopName,String shopAddress, String shopMobile){
        this.medicine=medicine;
        this.strength=strength;
        this.manufacturer=manufacturer;
        this.shopName=shopName;
        this.shopAddress=shopAddress;
        this.shopMobile=shopMobile;




    }

    public String getMedicine(){
        return  medicine;
    }
    public String getStrength(){
        return  strength;
    }
    public String getManufacturer(){
        return  manufacturer;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopMobile() {
        return shopMobile;
    }




}
