package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


import android.database.Cursor;
import android.widget.Toast;

public class Old_Weather extends AppCompatActivity {
    int i = 0,j = 0;
    String getTown;
    String[] a;
    Cursor cursor;
    String Da = "0", Te = "0", We = "0", Wi = "0", Hu = "0";
    DatabaseHandler DB = new DatabaseHandler(this);
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

        MyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int item = position;

                new AlertDialog.Builder(Old_Weather.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Είσαι σίγουρος;")
                        .setMessage("Πρόκειται να διαγραφεί αυτή η εγγραφή")
                        .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
        a = new String[cursor.getCount() * 5];
        viewData(weatherList);
        // Toast.makeText(getApplicationContext(),a[2],Toast.LENGTH_LONG).show();
    }

    public void setData(String d,String t,String we,String wi,String h){
        this.Da = d;
        this.Te = t;
        this.We = we;
        this.Wi = wi;
        this.Hu = h;
    }

    public void viewData(ArrayList<WeatherList> lista){
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"NO DATA TO PREVIEW ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext() ) {
                a[i] = cursor.getString(6);
                a[i+1] = cursor.getString(2);
                a[i+2] = cursor.getString(3);
                a[i+3] = cursor.getString(4);
                a[i+4] = cursor.getString(5);
                setData(a[i],a[i+1],a[i+2],a[i+3],a[i+4]);
                i+=5;
                addPins(lista);
            }

        }
    }

    public void addPins(ArrayList<WeatherList> list){
        WeatherList history = new WeatherList(Da,Te,We,Wi,Hu);
        list.add(history);
    }
}




