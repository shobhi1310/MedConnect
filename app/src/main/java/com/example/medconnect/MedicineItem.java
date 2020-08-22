package com.example.medconnect;

public class MedicineItem {
    private int imageR;
    private String text1;
    private String text2;

    public MedicineItem(int imageR,String text1,String text2){
        this.imageR = imageR;
        this.text1 = text1;
        this.text2 = text2;
    }


    //getter methods
    public int getImageR(){
        return this.imageR;
    }

    public String getText1(){
        return this.text1;
    }

    public String gettext2(){
        return this.text2;
    }

}
