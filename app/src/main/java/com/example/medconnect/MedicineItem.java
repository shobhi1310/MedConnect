package com.example.medconnect;

public class MedicineItem {
//    private int imageR;
    private String medicineName;
    private String manufacturer;
//    private boolean status;
    private String weight;

    public MedicineItem(String medicineName,String manufacturer,String weight){
//        this.imageR = imageR;
        this.medicineName = medicineName;
        this.manufacturer=manufacturer;
//        this.status=status;
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


//    public boolean getStatus(){
//        return this.status;
//    }

}
