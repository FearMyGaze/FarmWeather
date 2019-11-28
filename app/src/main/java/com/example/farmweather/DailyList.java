package com.example.farmweather;

public class DailyList {
    private Integer Id;
    private String Time;
    private String TempMax;
    private String TempMin;
    private String Summary;
    private String City;

    public DailyList(Integer id , String time , String tempMax , String tempMin , String summary , String city){
        this.Id = id;
        this.Time = time;
        this.TempMax = tempMax;
        this.TempMin = tempMin;
        this.Summary = summary;
        this.City = city;
    }

    public String getTime() { return Time;}

    public String getTempMax() { return TempMax;}

    public String getTempMin() { return TempMin;}

    public String getSummary() { return Summary;}

}
