package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
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

    String getTown;
    Cursor cursor;
    String Date = "0", Temp = "0", Weather = "0", Wind = "0", Humidity = "0";
    int item = 0,Id = 0;
    WeatherList history;

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
                                " Θερμοκρασία: " + weatherList.get(item).getTemp() +
                                " Περιγραφή: " + weatherList.get(item).getWeather() +
                                " Αέρας: " + weatherList.get(item).getWind() +
                                " Υγρασία: " + weatherList.get(item).getHumidity() + ". Για διαγραφή πιέστε συνεχόμενα την εγγραφή!")
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
                                Integer deleteRows = DB.deleteDate(getTown,weatherList.get(item).getTemp(),weatherList.get(item).getWeather(),weatherList.get(item).getWind(),weatherList.get(item).getHumidity(),weatherList.get(item).getDate());
                                if(deleteRows > 0){
                                    Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Cannot delete data",Toast.LENGTH_LONG).show();
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

        switch(item.getItemId()){
            case R.id.Clear:
                    Toast.makeText(this,"This is a message from a far far away universe",Toast.LENGTH_SHORT).show();//ADEFRFE AFTO THA TO GAMIS KAI THA BALS TO SELECT S
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    public void mergeIconRows(int ID, int iconID){
        isUpdated = DB.updateIconID(ID,iconID);
        if(isUpdated == false) {
            Toast.makeText(getApplicationContext(), "Error while updating data", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewData(ArrayList<WeatherList> lista){
        int i = 0;
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"NO DATA TO PREVIEW ",Toast.LENGTH_SHORT).show();
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
        }

    }

    public void addPins(ArrayList<WeatherList> list){
        history = new WeatherList(Id,item,Date,Temp,Weather,Wind,Humidity);
        list.add(history);
    }

}




