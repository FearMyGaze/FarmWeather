package com.example.farmweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Old_Weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old__weather);


        ListView MyList = (ListView) findViewById(R.id.ListView);
        //BGALE TA // GIA NA DEIS TI MAGIA TIS DISNEY

        //WeatherList day1 = new WeatherList("14/5/10","20C","Καθαρός","0.23","90%");
        //WeatherList day2 = new WeatherList("12/1/20","29C","Καθαρός","0.66","56%");


        ArrayList<WeatherList> weatherList = new ArrayList<>();
        //weatherList.add(day1);
        //weatherList.add(day2);


        CustomAdapter adapter = new CustomAdapter(this , R.layout.adapter_view_layout, weatherList);
        MyList.setAdapter(adapter);

    }
}
