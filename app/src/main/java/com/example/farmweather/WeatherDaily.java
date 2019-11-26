package com.example.farmweather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class WeatherDaily extends AppCompatActivity {

    String API= "e1ddb97838fe7ea715475061978bb3b4";
    //DatabaseHandler DataBase = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);

        final ListView MyDailyList = findViewById(R.id.WeatherDayListView);
        final ArrayList<DailyList> dailyList = new ArrayList<>();
        final DailyAdapter adapter = new DailyAdapter(this,R.layout.adapter_daily,dailyList);
        MyDailyList.setAdapter(adapter);


    }
}
