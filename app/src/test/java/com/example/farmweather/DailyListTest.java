package com.example.farmweather;

import org.junit.Test;
import static org.junit.Assert.*;

public class DailyListTest {
    String time = "20/9/2019";
    String maxTemp = "20";
    String minTemp = "12";
    String summary = "Βροχερός";
    String city = "Serres";



    @Test
    public void getTime() {
        String expected = "20/9/2019";
        DailyList dailyList = new DailyList(1 , 1 ,expected,maxTemp,minTemp,city , summary);
        assertEquals(dailyList.getTime(),time);
    }

    @Test
    public void getTempMax() {
        String expected = "20";
        DailyList dailyList = new DailyList(1 , 1 ,time,expected,minTemp,city , summary);
        assertEquals(dailyList.getTempMax(),maxTemp);
    }

    @Test
    public void getTempMin() {
        String expected = "12";
        DailyList dailyList = new DailyList(1 , 1 ,time,maxTemp,expected,city , summary);
        assertEquals(dailyList.getTempMin(),minTemp);
    }

    @Test
    public void getSummary() {
        String expected = "Βροχερός";
        DailyList dailyList = new DailyList(1 , 1 ,time,maxTemp,minTemp,city , expected);
        assertEquals(dailyList.getSummary(),summary);
    }

    @Test
    public void getCity() {
        String expected = "Serres";
        DailyList dailyList = new DailyList(1 , 1 ,time,maxTemp,minTemp,expected , summary);
        assertEquals(dailyList.getCity(),city);
    }

    @Test
    public void getIconId() {
    }

    @Test
    public void getId() {
    }
}