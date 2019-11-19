package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Old_Weather extends AppCompatActivity {

<<<<<<< Updated upstream
    String getTown;

=======
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old__weather);
<<<<<<< Updated upstream
        getTown = (getIntent().getStringExtra("GetTown"));


        final ListView MyList = findViewById(R.id.ListView);


        //BGALE TA // GIA NA DEIS TI MAGIA TIS DISNEY

        //WeatherList day1 = new WeatherList("14/5/10","20C","Καθαρός","0.23","90%");
        //WeatherList day2 = new WeatherList("12/1/20","29C","Καθαρός","0.66","56%");


=======
        final ListView MyList = findViewById(R.id.ListView);

        WeatherList History = new WeatherList("1","2","3","4","5");
>>>>>>> Stashed changes
        final ArrayList<WeatherList> weatherList = new ArrayList<>();
        weatherList.add(History);
        //KWDIKAS GIA DIAGRAFH ANTIKEIMENOY APO LISTA AN PATHSEIS SYNEXOMENA
        final CustomAdapter adapter = new CustomAdapter(this , R.layout.adapter_view_layout, weatherList);
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
                        .setNegativeButton("Όχι",null)
                        .show();
                return true;
            }
        });


    }
}
