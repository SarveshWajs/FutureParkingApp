package com.example.testing;

public class FetchVehicleData {
    String vehicleNumber;
    String model;
    String description;
    String compound;
    String price;



    public FetchVehicleData() {
    }

    public FetchVehicleData(String vehicleNumber, String model, String description,String compound , String price) {
        this.vehicleNumber = vehicleNumber;
        this.model = model;
        this.description = description;
        this.compound = compound;
        this.price = price;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}