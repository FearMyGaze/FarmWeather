package com.example.farmweather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;

public class WeatherDaily extends AppCompatActivity {

    Boolean isPUpdated;
    Cursor cursor;

    Integer switcher,Id;
    String Time = "0",MinTemp = "0",MaxTemp = "0",Summary = "0",City = "0",sort = "DESC";
    DailyList perHour;
    String API= "e1ddb97838fe7ea715475061978bb3b4"; //DarkSky
    DatabaseHandler DataBase = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);
        final ListView MyDailyList = findViewById(R.id.SelectTownListView);
        final ListView MyDailyList1 = findViewById(R.id.HistoryListView);
        final ArrayList<DailyList> dailyList = new ArrayList<>();
        final DailyAdapter adapter = new DailyAdapter(this,R.layout.adapter_daily,dailyList);
        MyDailyList.setAdapter(adapter);
        MyDailyList1.setAdapter(adapter);
        viewPData(dailyList);
    }

    public void mergeIconRows(int ID, int iconID){
        isPUpdated = DataBase.updateIconID(ID,iconID);
        if(isPUpdated == false) {
            Toast.makeText(getApplicationContext(), "Σφάλμα κατά την ενημέρωση", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewPData(ArrayList<DailyList> lista){
        cursor = DataBase.getPData(sort);
        int i = 0;
        switcher = 1;
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext() ) {
                Id = Integer.valueOf(cursor.getString(0));
                Time = cursor.getString(2);
                MinTemp = cursor.getString(3);
                MaxTemp = cursor.getString(4);
                Summary = cursor.getString(5);
                City = cursor.getString(6);
                addPins(lista);
                //mergeIconRows(Id,item+i);
                //i++;
            }
            cursor.close();
        }
    }

    public void addPins(ArrayList<DailyList> list){
        perHour = new DailyList(Id,Time,MinTemp,MaxTemp,Summary,City);
        list.add(perHour);
    }
}
