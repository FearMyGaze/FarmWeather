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
    Cursor cursor,cursor1,cursor2,cursor3;
    String Date = "0", Temp = "0", Weather = "0", Wind = "0", Humidity = "0", sort = "DESC";
    int item = 0,Id = 0,switcher = 0;
    WeatherList history;
    ArrayList<WeatherList> balander,boomer = new ArrayList<>();
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
        cursor = DB.getData(getTown,sort);
        viewData(weatherList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        return true;
    }

    public void deleteall(){
        Integer clearall = DB.clearall(getTown);
        if (clearall > 0) {
            Toast.makeText(getApplicationContext(), "Η διαγραφή του ιστορικού ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Αδυναμία διαγραφής ιστορικού", Toast.LENGTH_SHORT).show();
        }
        balander = new ArrayList<>();
        final CustomAdapter adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
        final ListView list = findViewById(R.id.ListView);
        list.setAdapter(adaptori);
        adaptori.notifyDataSetChanged();
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
                                    deleteall();
                            }
                        }).show();
                return true;

            case R.id.Clear:
                choice = "Καθαρός";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.RainSky:
                choice = "Βροχερός";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Storm:
                choice = "Καταιγίδα";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Sun:
                choice = "Λιακάδα";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Heat:
                choice = "Καύσωνας";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Snow:
                choice = "Χιόνι";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Smoke:
                choice = "Σκόνη";
                balander = new ArrayList<>();
                viewSortedBwData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_1:
                choice = "Jan";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_2:
                choice = "Feb";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_3:
                choice = "Mar";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_4:
                choice = "Apr";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_5:
                choice = "May";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_6:
                choice = "Jun";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_7:
                choice = "Jul";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_8:
                choice = "Aug";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_9:
                choice = "Sep";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_10:
                choice = "Oct";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_11:
                choice = "Nov";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_12:
                choice = "Dec";
                balander = new ArrayList<>();
                viewSortedBmData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList_0_10:
                choice = "1";
                balander = new ArrayList<>();
                viewSortedBtData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList_11_25:
                choice = "2";
                balander = new ArrayList<>();
                viewSortedBtData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList26_40:
                choice = "3";
                balander = new ArrayList<>();
                viewSortedBtData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempListUnder0:
                choice = "4";
                balander = new ArrayList<>();
                viewSortedBtData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempListUpper40:
                choice = "5";
                balander = new ArrayList<>();
                viewSortedBtData(balander);
                adaptori = new CustomAdapter(this, R.layout.adapter_view_layout, balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.MaxItemList:
                sort = "ASC";
                return true;

            case R.id.MinItemList:
                sort = "DESC";
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
        switcher = 1;
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
                pass(boomer);
            }
            cursor.close();
        }

    }

    public void viewSortedBwData(ArrayList<WeatherList> lista){
        cursor1 = DB.sortByWeatherStatus(getTown,choice,sort);
        int i = 0;
        switcher = 2;
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
                pass(boomer);
            }
            cursor1.close();
        }
    }

    public void viewSortedBmData(ArrayList<WeatherList> lista){
        cursor2 = DB.sortByMonthStatus(getTown,choice,sort);
        int i = 0;
        switcher = 3;
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
                pass(boomer);
            }
            cursor2.close();
        }
    }

    public void viewSortedBtData(ArrayList<WeatherList> lista){
        cursor3 = DB.sortByTempStatus(getTown,choice,sort);
        int i = 0;
        switcher = 4;
        if(cursor3.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor3.moveToNext() ) {
                Id = Integer.valueOf(cursor3.getString(0));
                Date = cursor3.getString(6);
                Temp = cursor3.getString(2);
                Weather = cursor3.getString(3);
                Wind = cursor3.getString(4);
                Humidity = cursor3.getString(5);
                addPins(lista);
                mergeIconRows(Id,item+i);
                i++;
                pass(boomer);
            }
            cursor3.close();
        }
    }

    public void addPins(ArrayList<WeatherList> list){
        history = new WeatherList(Id,item,Date,Temp,Weather,Wind,Humidity);
        list.add(history);
    }

    public void pass(ArrayList<WeatherList> list){

        for(int i=0;i<list.size();i++){
            boomer.remove(i);
        }

        for(int i=0;i<list.size();i++){
            boomer.add(list.get(i));
        }

    }

//    public void swap(int switcher,String sort){
//        this.sort = sort;
//        switch(switcher){
//            case 1:
//                viewSortedBwData(balander);
//                break;
//            case 2:
//                viewSortedBmData(balander);
//                break;
//            case 3:
//                viewSortedBtData(balander);
//                break;
//            case 4:
//                viewData(balander);
//                break;
//        }
//    }

}




