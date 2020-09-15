package com.FearMyGaze.FarmWeather;

public class CityAddList {
    private String addCity;
    private int IconID,CityID;

    public CityAddList(int cityID,String city, int iconID){
        this.CityID = cityID;
        this.addCity = city;
        this.IconID = iconID;
    }
    public void setIconId(int iconID){
        this.IconID = iconID;
    }
    public String getCity(){ return addCity; }
    public int getCityID(){ return CityID; }
}
