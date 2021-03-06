package com.example.medconnect;

import java.io.Serializable;

public class MedicineItem implements Serializable {
//    private int imageR;
    private String medicineName;
    private String manufacturer;
//    private boolean status;
    private String weight;
    private String id;
    private Boolean prescription;

    public MedicineItem(String id,String medicineName,String manufacturer,String weight, Boolean prescription){
//        this.imageR = imageR;
        this.id=id;
        this.medicineName = medicineName;
        this.manufacturer=manufacturer;
//        this.status=status;
        this.weight=weight;
        this.prescription = prescription;
    }


    //getter methods
//    public int getImageR(){
//        return this.imageR;
//    }


    public String getId() {
        return id;
    }

    public String getMedicineName(){
        return this.medicineName;
    }

    public String getManufacturer(){
        return this.manufacturer;
    }

    public String getWeight(){
        return this.weight;
    }

    public Boolean getPrescription() { return this.prescription; }

//    public boolean getStatus(){
//        return this.status;
//    }

}
