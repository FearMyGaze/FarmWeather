package com.example.farmweather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import android.database.Cursor;
import android.widget.Toast;

public class Old_Weather extends AppCompatActivity {

    String getTown,choice;
    Cursor cursor,cursor1,cursor2,cursor3;
    String Date = "0", City = "0", Temp = "0", Weather = "0", Wind = "0", Humidity = "0", sort = "DESC";
    int item = 0,Id = 0,switcher = 0;
    WeatherList history;
    ArrayList<WeatherList> balander;
    DatabaseHandler DB = new DatabaseHandler(this);
    boolean isUpdated,flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old__weather);

        getTown = (getIntent().getStringExtra("GetTown"));

        final ListView MyList = findViewById(R.id.ListView);

        final ArrayList<WeatherList> weatherList = new ArrayList<>();



        //KWDIKAS GIA DIAGRAFH ANTIKEIMENOY APO LISTA AN PATHSEIS SYNEXOMENA

        final CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_view_layout, weatherList, balander);
        MyList.setAdapter(adapter);

        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int item = position;
                if(isTF()){
                    new AlertDialog.Builder(Old_Weather.this)
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .setTitle("Πληροφορίες")
                            .setMessage("Ημερομηνία: " + weatherList.get(item).getDate() +
                                    "\nΘερμοκρασία: " + weatherList.get(item).getTemp() +
                                    "\nΠεριγραφή: " + weatherList.get(item).getWeather() +
                                    "\nΑέρας: " + weatherList.get(item).getWind() +
                                    "\nΥγρασία: " + weatherList.get(item).getHumidity() +
                                    "\nΠόλη: " + weatherList.get(item).getCity() + "\nΓια διαγραφή πιέστε συνεχόμενα την εγγραφή!")
                            .setNegativeButton("Ok" ,null)
                            .show();
                }
                else{

                    new AlertDialog.Builder(Old_Weather.this)
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .setTitle("Πληροφορίες")
                            .setMessage("Ημερομηνία: " + balander.get(item).getDate() +
                                    "\nΘερμοκρασία: " + balander.get(item).getTemp() +
                                    "\nΠεριγραφή: " + balander.get(item).getWeather() +
                                    "\nΑέρας: " + balander.get(item).getWind() +
                                    "\nΥγρασία: " + balander.get(item).getHumidity() +
                                    "\nΠόλη: " + balander.get(item).getCity() + "\nΓια διαγραφή πιέστε συνεχόμενα την εγγραφή!")
                            .setNegativeButton("Ok" ,null)
                            .show();
                }

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
                        .setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(getTown != "*") {
                                    Integer deleteRows = DB.deleteDate(String.valueOf(weatherList.get(item).getId()), getTown, weatherList.get(item).getTemp(), weatherList.get(item).getWeather(), weatherList.get(item).getWind(), weatherList.get(item).getHumidity(), weatherList.get(item).getDate());
                                    if (deleteRows > 0) {
                                        Toast.makeText(Old_Weather.this, "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Δεν βρέθηκαν παρελθοντικά δεδομένα", Toast.LENGTH_SHORT).show();
                                    }
                                    weatherList.remove(item);
                                    adapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Παρακαλώ επαναφέρετε το ιστορικό μιας πόλης",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("ΟΧΙ", null)
                        .show();
                return true;
            }
        });
        viewData(weatherList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        return true;
    }

    public void deleteAll(){
        Integer ClearAll = DB.clearall(getTown);
        if (ClearAll > 0) {
            Toast.makeText(getApplicationContext(), "Η διαγραφή του ιστορικού ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Αδυναμία διαγραφής ιστορικού", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // DILOSI ANTIKEIMENON GIA TIN LISTA STO OLD_WEATHER MENU

        balander = new ArrayList<>();
        final SubAdapter adaptori = new SubAdapter(this, R.layout.adapter_view_layout,balander);
        final ListView list = findViewById(R.id.ListView);
        final ImageView DailySummaryIcon = findViewById(R.id.DailySummaryIcon);

        switch(item.getItemId()){
            case R.id.DeleteItemList:
                new AlertDialog.Builder(Old_Weather.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("ΠΡΟΣΟΧΗ !!")
                        .setMessage("Θέλετε να διαγράψετε ολα τα αντικείμενα ;")
                        .setNegativeButton("ΟΧΙ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewData(balander);
                                list.setAdapter(adaptori);
                                adaptori.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(getTown != "*"){
                                    deleteAll();
                                    Toast.makeText(getApplicationContext(),"Η διαγραφή του ιστορικού ολοκληρώθηκε",Toast.LENGTH_SHORT).show();
                                    list.setAdapter(adaptori);
                                    adaptori.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Παρακαλώ επαναφέρετε το ιστορικό μιας πόλης",Toast.LENGTH_SHORT).show();
                                    viewData(balander);
                                    list.setAdapter(adaptori);
                                    adaptori.notifyDataSetChanged();
                                }
                            }
                        }).show();
                return true;

            case R.id.Clear:
                flag = true;
                choice = "Καθαρός";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.RainSky:
                flag = true;
                choice = "Βροχερός";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Storm:
                flag = true;
                choice = "Καταιγίδα";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Sun:
                flag = true;
                choice = "Λιακάδα";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Heat:
                flag = true;
                choice = "Καύσωνας";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Snow:
                flag = true;
                choice = "Χιόνι";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Smoke:
                flag = true;
                choice = "Σκόνη";
                viewSortedBwData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_1:
                flag = true;
                choice = "Jan";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_2:
                flag = true;
                choice = "Feb";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_3:
                flag = true;
                choice = "Mar";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_4:
                flag = true;
                choice = "Apr";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_5:
                flag = true;
                choice = "May";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_6:
                flag = true;
                choice = "Jun";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_7:
                flag = true;
                choice = "Jul";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_8:
                flag = true;
                choice = "Aug";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_9:
                flag = true;
                choice = "Sep";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_10:
                flag = true;
                choice = "Oct";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_11:
                flag = true;
                choice = "Nov";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.Month_12:
                flag = true;
                choice = "Dec";
                viewSortedBmData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList_0_10:
                flag = true;
                choice = "1";
                viewSortedBtData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList_11_25:
                flag = true;
                choice = "2";
                viewSortedBtData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempList26_40:
                flag = true;
                choice = "3";
                viewSortedBtData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempListUnder0:
                flag = true;
                choice = "4";
                viewSortedBtData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.TempListUpper40:
                flag = true;
                choice = "5";
                viewSortedBtData(balander);
                list.setAdapter(adaptori);
                adaptori.notifyDataSetChanged();
                return true;

            case R.id.ShowAllItemList:
                flag = true;
                swap(switcher, sort, balander);
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

    public boolean isTF(){
        if(flag == false){
            return true;
        }else{
            return false;
        }
    }

    public void viewData(ArrayList<WeatherList> lista){
        cursor = DB.getData(getTown,sort);
        int i = 0;
        switcher = 1;
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext() ) {
                Id = Integer.valueOf(cursor.getString(0));
                City = cursor.getString(1);
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
        cursor1 = DB.sortByWeatherStatus(getTown,choice,sort);
        int i = 0;
        switcher = 2;
        if(cursor1.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor1.moveToNext() ) {
                Id = Integer.valueOf(cursor1.getString(0));
                City = cursor1.getString(1);
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
        cursor2 = DB.sortByMonthStatus(getTown,choice,sort);
        int i = 0;
        switcher = 3;
        if(cursor2.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor2.moveToNext() ) {
                Id = Integer.valueOf(cursor2.getString(0));
                City = cursor2.getString(1);
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
                City = cursor3.getString(1);
                Date = cursor3.getString(6);
                Temp = cursor3.getString(2);
                Weather = cursor3.getString(3);
                Wind = cursor3.getString(4);
                Humidity = cursor3.getString(5);
                addPins(lista);
                mergeIconRows(Id,item+i);
                i++;
            }
            cursor3.close();
        }
    }

    public void addPins(ArrayList<WeatherList> list){
        history = new WeatherList(Id,item,City,Date,Temp,Weather,Wind,Humidity);
        list.add(history);
    }

    public void swap(int switcher,String sort,ArrayList<WeatherList> list){
        this.sort = sort;
        switch(switcher){
            case 1:
                getTown = "*";
                viewData(list);
                break;
            case 2:
                getTown = "*";
                viewSortedBwData(list);
                break;
            case 3:
                getTown = "*";
                viewSortedBmData(list);
                break;
            case 4:
                getTown = "*";
                viewSortedBtData(list);
                break;
        }

    }
}




