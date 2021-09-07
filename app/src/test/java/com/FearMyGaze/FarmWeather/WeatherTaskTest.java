package com.FearMyGaze.FarmWeather;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class WeatherTaskTest {

    @Test
    public void getReading(){
        String json = "{\"coord\":{\"lon\":23.55,\"lat\":41.09},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":11.6,\"pressure\":1014,\"humidity\":69,\"temp_min\":10,\"temp_max\":12.78},\"wind\":{\"speed\":0.29,\"deg\":119},\"clouds\":{\"all\":0},\"dt\":1575902699,\"sys\":{\"type\":3,\"id\":18806,\"country\":\"GR\",\"sunrise\":1575869935,\"sunset\":1575903436},\"timezone\":7200,\"id\":734330,\"name\":\"Serres\",\"cod\":200}\n";

        WeatherTask weatherTask = new WeatherTask(null);

        TemperatureReading actual = weatherTask.getReading(json,true);

        TemperatureReading expected = new TemperatureReading(1575902699L,
                "10",
                "13",
                "Καθαρός",
                "Serres");


        assertThat(actual,samePropertyValuesAs(expected));
    }

    @Test
    public void getMultipleReadings() {
        String readingOne = "{\"coord\":{\"lon\":23.55,\"lat\":41.09},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":11.6,\"pressure\":1014,\"humidity\":69,\"temp_min\":10,\"temp_max\":12.78},\"wind\":{\"speed\":0.29,\"deg\":119},\"clouds\":{\"all\":0},\"dt\":1575902699,\"sys\":{\"type\":3,\"id\":18806,\"country\":\"GR\",\"sunrise\":1575869935,\"sunset\":1575903436},\"timezone\":7200,\"id\":734330,\"name\":\"Serres\",\"cod\":200}\n";
        String readingTwo = "{\"coord\":{\"lon\":23.55,\"lat\":41.09},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":11.6,\"pressure\":1014,\"humidity\":69,\"temp_min\":10,\"temp_max\":13.78},\"wind\":{\"speed\":0.29,\"deg\":119},\"clouds\":{\"all\":0},\"dt\":1575902699,\"sys\":{\"type\":3,\"id\":18806,\"country\":\"GR\",\"sunrise\":1575869935,\"sunset\":1575903436},\"timezone\":7200,\"id\":734330,\"name\":\"Drama\",\"cod\":200}\n";

        List<String> readings = new ArrayList<>();
        readings.add(readingOne);
        readings.add(readingTwo);


        WeatherTask weatherTask = new WeatherTask(null);
        List<TemperatureReading> actual = weatherTask.getReadings(readings,true);


        TemperatureReading expectedOne = new TemperatureReading(1575902699L,
                "10",
                "13",
                "Καθαρός",
                "Serres");

        TemperatureReading expectedTwo = new TemperatureReading(1575902699L,
                "10",
                "14",
                "Καθαρός",
                "Drama");

        assertThat(actual.get(0),samePropertyValuesAs(expectedOne));
        assertThat(actual.get(1),samePropertyValuesAs(expectedTwo));

    }
}