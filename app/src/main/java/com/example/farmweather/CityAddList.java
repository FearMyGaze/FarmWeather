package com.example.farmweather;

public class CityAddList {
    private String addCity;
    private int IconID;

    public CityAddList(String city, int iconID){
        this.addCity = city;
        this.IconID = iconID;
    }
    public void setIconId(int iconID){
        this.IconID = iconID;
    }

    public String getCity(){ return addCity; }

}
