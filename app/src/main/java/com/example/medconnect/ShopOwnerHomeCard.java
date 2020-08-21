package com.example.medconnect;

public class ShopOwnerHomeCard {
    private String medicine;
    private String strength;
    private String manufacturer;
    private String status;

    public  ShopOwnerHomeCard(String medicine,String strength,String manufacturer,String status){
        this.medicine=medicine;
        this.strength=strength;
        this.manufacturer=manufacturer;
        this.status=status;

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
    public String getStatus(){
        return  status;
    }



}
