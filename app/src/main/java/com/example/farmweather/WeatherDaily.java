package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;

public class WeatherDaily extends AppCompatActivity {

    Boolean isPUpdated;
    Cursor cursor,cursor1;

    Integer switcher,Id=0,cId=0;
    String Time = "0",MinTemp = "0",MaxTemp = "0",Summary = "0",City = "0",sort = "DESC";
    String cCity="0",cMinTemp = "0",cMaxTemp = "0",cSummary = "0",cTime = "0";
    DailyList perHour,perHour1;
    String API= "e1ddb97838fe7ea715475061978bb3b4"; //DarkSky
    DatabaseHandler DataBase = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);
        final ListView MyDailyList = findViewById(R.id.SelectTownListView);
        final ListView MyDailyList1 = findViewById(R.id.HistoryListView);
        final ArrayList<DailyList> dailyList = new ArrayList<>();
//        final ArrayList<DailyList> dailyList1 = new ArrayList<>();
        final DailyAdapter adapter = new DailyAdapter(this,R.layout.adapter_daily,dailyList);
//        final DailyAdapter adapter1 = new DailyAdapter(this,R.layout.adapter_daily,dailyList1);
        MyDailyList.setAdapter(adapter);
//        MyDailyList1.setAdapter(adapter1);
        MyDailyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int item = i;
//                new AlertDialog.Builder(WeatherDaily.this)
//                        .setIcon(android.R.drawable.ic_menu_info_details)
//                        .setTitle("Πληροφορίες")
//                        .setMessage("Πόλη: " + dailyList.get(item).getCity()+
//                                "\nΕλάχιστη θερμοκρασία: " + dailyList.get(item).getTempMin() +
//                                "\nΜέγιστη θερμοκρασία: " + dailyList.get(item).getTempMax() +
//                                "\nΣύνοψη: " + dailyList.get(item).getSummary() +
//                                "\nΗμ/νια και Ώρα: " + dailyList.get(item).getTime() + "\nΓια διαγραφή πιέστε συνεχόμενα την εγγραφή!")
//                        .setNegativeButton("Ok" ,null)
//                        .show();
            }
        });
        MyDailyList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
//        viewCity(dailyList1);
        viewPData(dailyList);
    }

    public void mergeIconRows(int ID, int iconID){
        isPUpdated = DataBase.updateIconID(ID,iconID);
        if(isPUpdated == false) {
            Toast.makeText(getApplicationContext(), "Σφάλμα κατά την ενημέρωση", Toast.LENGTH_SHORT).show();
        }
    }

//    public void viewCity(ArrayList<DailyList> list){
//        cursor1 = DataBase.getCData(sort);
//        if(cursor1.getCount() == 0){
//            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            while (cursor1.moveToNext() ) {
//                cId = Integer.valueOf(cursor1.getString(0));
//                cCity = cursor1.getString(1);
//                addPins1(list);
//            }
//            cursor1.close();
//        }
//
//    }

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
    public void addPins1(ArrayList<DailyList> list){
        perHour1 = new DailyList(cId,cCity,"axne","axne","axne","axne");
        list.add(perHour1);
    }
}
