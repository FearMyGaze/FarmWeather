package com.example.farmweather;

import org.junit.Test;
import static org.junit.Assert.*;

public class CityAddListTest {
    String city = "Lamia";
    Integer cityId = 3;

    @Test
    public void getCity() {
        String expected = "Lamia";
        CityAddList cityAddList = new CityAddList(3,expected,1);
        assertEquals(cityAddList.getCity(),city);
    }

    @Test
    public void getCityId(){
        CityAddList list = new CityAddList(20 , "Kavala" , 2 );
        CityAddList testList = new CityAddList(20 , "Kavala" , 2 );
        assertEquals(list.getCityID(),testList.getCityID());
    }
}