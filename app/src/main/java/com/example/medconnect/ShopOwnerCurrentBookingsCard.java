package com.example.medconnect;

public class ShopOwnerCurrentBookingsCard {
    private String medicine;
    private String strength;
    private String manufacturer;
    private String customerName;
    private String customerMobile;
    private String bookingDate;





    public  ShopOwnerCurrentBookingsCard(String medicine,String strength,String manufacturer,String customerName,String customerMobile,String bookingDate){
        this.medicine=medicine;
        this.strength=strength;
        this.manufacturer=manufacturer;
        this.customerName=customerName;
        this.customerMobile=customerMobile;
        this.bookingDate=bookingDate;



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

    public String getBookingDate() {
        return bookingDate;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getCustomerName() {
        return customerName;
    }
}
