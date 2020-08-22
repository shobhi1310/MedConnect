package com.example.medconnect;

public class ShopOwnerHomeCard {
    private String medicine;
    private String strength;
    private String manufacturer;
    private boolean status;

    public  ShopOwnerHomeCard(String medicine,String strength,String manufacturer,Boolean status){
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
    public boolean getStatus(){
        return  status;
    }
    public void setStatus(boolean newStatus){
        this.status=newStatus;
    }



}
