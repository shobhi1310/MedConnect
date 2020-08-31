package com.example.medconnect;

public class ShopOwnerSearchMedicineCard {

    private String medicineName;
    private String manufacturer;
    private String id;

    private String weight;

    public ShopOwnerSearchMedicineCard(String id,String medicineName,String manufacturer,String weight){
//        this.imageR = imageR;
        this.medicineName = medicineName;
        this.manufacturer=manufacturer;
        this.id=id;

        this.weight=weight;
    }


    //getter methods
//    public int getImageR(){
//        return this.imageR;
//    }

    public String getMedicineName(){
        return this.medicineName;
    }

    public String getId() {
        return id;
    }

    public String getManufacturer(){
        return this.manufacturer;
    }

    public String getWeight(){
        return this.weight;
    }
}
