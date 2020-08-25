package com.example.medconnect;

public class ShopOwnerSearchMedicineCard {

    private String medicineName;
    private String manufacturer;

    private String weight;

    public ShopOwnerSearchMedicineCard(String medicineName,String manufacturer,String weight){
//        this.imageR = imageR;
        this.medicineName = medicineName;
        this.manufacturer=manufacturer;

        this.weight=weight;
    }


    //getter methods
//    public int getImageR(){
//        return this.imageR;
//    }

    public String getMedicineName(){
        return this.medicineName;
    }

    public String getManufacturer(){
        return this.manufacturer;
    }

    public String getWeight(){
        return this.weight;
    }
}
