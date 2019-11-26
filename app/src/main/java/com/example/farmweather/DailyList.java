package com.example.farmweather;

public class DailyList {

    private String Time;
    private String TempMax;
    private String TempMin;
    private String Summary;

    public DailyList(String time , String tempMax , String tempMin , String summary){

        this.Time = time;
        this.TempMax = tempMax;
        this.TempMin = tempMin;
        this.Summary = summary;
    }

    public String getTime() { return Time;}

    public String getTempMax() { return TempMax;}

    public String getTempMin() { return TempMin;}

    public String getSummary() { return Summary;}

}
