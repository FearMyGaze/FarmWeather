package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import android.database.Cursor;
import android.widget.Toast;

public class Old_Weather extends AppCompatActivity {


    String getTown,choice;
    Cursor cursor,cursor1,cursor2;
    String Date = "0", Temp = "0", Weather = "0", Wind = "0", Humidity = "0";
    int item = 0,Id = 0;
    WeatherList history;
    ArrayList<WeatherList> monthList;
    DatabaseHandler DB = new DatabaseHandler(this);
    boolean isUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old__weather);

        getTown = (getIntent().getStringExtra("GetTown"));

        final ListView MyList = findViewById(R.id.ListView);
        final ArrayList<WeatherList> weatherList = new ArrayList<>();

        //KWDIKAS GIA DIAGRAFH ANTIKEIMENOY APO LISTA AN PATHSEIS SYNEXOMENA
        final CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_view_layout, weatherList);
        MyList.setAdapter(adapter);

        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int item = position;

                new AlertDialog.Builder(Old_Weather.this)
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setTitle("Πληροφορίες")
                        .setMessage("Ημερομηνία: " + weatherList.get(item).getDate() +
                                "\nΘερμοκρασία: " + weatherList.get(item).getTemp() +
                                "\nΠεριγραφή: " + weatherList.get(item).getWeather() +
                                "\nΑέρας: " + weatherList.get(item).getWind() +
                                "\nΥγρασία: " + weatherList.get(item).getHumidity() + "\nΓια διαγραφή πιέστε συνεχόμενα την εγγραφή!")
                        .setNegativeButton("Ok" ,null)
                        .show();
            }
        });

        MyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = position;

                new AlertDialog.Builder(Old_Weather.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Είσαι σίγουρος;")
                        .setMessage("Πρόκειται να διαγραφεί αυτή η εγγραφή")
                        .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Integer deleteRows = DB.deleteDate(String.valueOf(weatherList.get(item).getId()),getTown,weatherList.get(item).getTemp(),weatherList.get(item).getWeather(),weatherList.get(item).getWind(),weatherList.get(item).getHumidity(),weatherList.get(item).getDate());
                                if(deleteRows > 0){
                                    Toast.makeText(Old_Weather.this, "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Δεν βρέθηκαν παρελθοντικά δεδομένα",Toast.LENGTH_SHORT).show();
                                }
                                weatherList.remove(item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Όχι", null)
                        .show();
                return true;
            }
        });
        cursor = DB.getData(getTown);
        viewData(weatherList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // DILOSI ANTIKEIMENON GIA TIN LISTA STO OLD_WEATHER MENU
        CustomAdapter adaptori;
        final ListView list = findViewById(R.id.ListView);
        switch(item.getItemId()){
            case R.id.DeleteItemList:
                new AlertDialog.Builder(Old_Weather.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("ΠΡΟΣΟΧΗ !!")
                        .setMessage("Θέλετε να διαγράψετε ολα τα αντικείμενα ;")
                        .setNegativeButton("ΟΧΙ",null)
                        .setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    Integer deleteall = DB.clearall(getTown);
                                    if (deleteall > 0) {
                                        Toast.makeText(getApplicationContext(), "Η διαγραφή του ιστορικού ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(getApplicationContext(), "Αδυναμία διαγραφής ιστορικού", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;

            case R.id.Clear:
                choice = "Καθαρός";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.RainSky:
                choice = "Βροχερός";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Storm:
                choice = "Καταιγίδα";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Sun:
                choice = "Λιακάδα";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Heat:
                choice = "Καύσωνας";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Snow:
                choice = "Χιόνι";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Smoke:
                choice = "Σκόνη";
                monthList = new ArrayList<>();
                viewSortedBwData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_1:
                choice = "Jan";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_2:
                choice = "Feb";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_3:
                choice = "Mar";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_4:
                choice = "Apr";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_5:
                choice = "May";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_6:
                choice = "Jun";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_7:
                choice = "Jul";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_8:
                choice = "Aug";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_9:
                choice = "Sep";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_10:
                choice = "Oct";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_11:
                choice = "Nov";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_12:
                choice = "Dec";
                monthList = new ArrayList<>();
                viewSortedBmData(monthList);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, monthList);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    public void mergeIconRows(int ID, int iconID){
        isUpdated = DB.updateIconID(ID,iconID);
        if(isUpdated == false) {
            Toast.makeText(getApplicationContext(), "Σφάλμα κατά την ενημέρωση", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData(ArrayList<WeatherList> lista){
        int i = 0;
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext() ) {
                Id = Integer.valueOf(cursor.getString(0));
                Date = cursor.getString(6);
                Temp = cursor.getString(2);
                Weather = cursor.getString(3);
                Wind = cursor.getString(4);
                Humidity = cursor.getString(5);
                addPins(lista);
                mergeIconRows(Id,item+i);
                i++;
            }
            cursor.close();
        }

    }

    public void viewSortedBwData(ArrayList<WeatherList> lista){
        cursor1 = DB.sortByWeatherStatus(getTown,choice);
        int i = 0;
        if(cursor1.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor1.moveToNext() ) {
                Id = Integer.valueOf(cursor1.getString(0));
                Date = cursor1.getString(6);
                Temp = cursor1.getString(2);
                Weather = cursor1.getString(3);
                Wind = cursor1.getString(4);
                Humidity = cursor1.getString(5);
                addPins(lista);
                mergeIconRows(Id,item+i);
                i++;
            }
            cursor1.close();
        }
    }

    public void viewSortedBmData(ArrayList<WeatherList> lista){
        cursor2 = DB.sortByMonthStatus(getTown,choice);
        int i = 0;
        if(cursor2.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor2.moveToNext() ) {
                Id = Integer.valueOf(cursor2.getString(0));
                Date = cursor2.getString(6);
                Temp = cursor2.getString(2);
                Weather = cursor2.getString(3);
                Wind = cursor2.getString(4);
                Humidity = cursor2.getString(5);
                addPins(lista);
                mergeIconRows(Id,item+i);
                i++;
            }
            cursor2.close();
        }
    }

    public void addPins(ArrayList<WeatherList> list){
        history = new WeatherList(Id,item,Date,Temp,Weather,Wind,Humidity);
        list.add(history);
    }
}




