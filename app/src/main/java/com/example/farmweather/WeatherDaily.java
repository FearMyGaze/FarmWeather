package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;

public class WeatherDaily extends AppCompatActivity {

    Boolean isPUpdated,isCUpdated;
    Cursor cursor,cursor1;

    Integer switcher,Id=0,cId=0,icId=0,icPid = 0,isCDeleted,isPDeleted;
    String Time = "0",MinTemp = "0",MaxTemp = "0",Summary = "0",City = "0",sort = "DESC";
    String cCity="0";
    DailyList perHour;
    CityAddList perHour1;
    String API= "e1ddb97838fe7ea715475061978bb3b4"; //DarkSky
    DatabaseHandler DataBase = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);

        final ListView addTownList = findViewById(R.id.SelectTownListView); //ListView ID For Cities
        final ListView MyDailyList1 = findViewById(R.id.HistoryListView); //ListView ID For History

        final ArrayList<DailyList> dailyList = new ArrayList<>(); //ArrayList For History
        final ArrayList<CityAddList> addTown = new ArrayList<>(); //ArrayList For Cities

        final DailyAdapter adapter = new DailyAdapter(this,R.layout.adapter_daily,dailyList); //Adapter For History
        final CityAddAdapter adapterTown = new CityAddAdapter(this,R.layout.adapter_city_add,addTown); //Adapter For Cities

        MyDailyList1.setAdapter(adapter); //Add Adapter History To ListView History
        addTownList.setAdapter(adapterTown); //Add Adapter Cities To ListView Cities


        MyDailyList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(WeatherDaily.this)
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setTitle("Πληροφορίες")
                        .setMessage("Πόλη: " + dailyList.get(i).getCity()+
                                "\nΕλάχιστη θερμοκρασία: " + dailyList.get(i).getTempMin() +
                                "\nΜέγιστη θερμοκρασία: " + dailyList.get(i).getTempMax() +
                                "\nΣύνοψη: " + dailyList.get(i).getSummary() +
                                "\nΗμ/νια και Ώρα: " + dailyList.get(i).getTime() + "\nΓια διαγραφή πιέστε συνεχόμενα την εγγραφή!")
                        .setNegativeButton("Ok" ,null)
                        .show();
            }
        });
        MyDailyList1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                icPid = position;
                isPDeleted = DataBase.deletePData(String.valueOf(icPid),dailyList.get(icPid).getCity());
                if (isPDeleted > 0) {
                    Toast.makeText(getApplicationContext(), "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();
                    dailyList.remove(icId);
                } else {
                    Toast.makeText(getApplicationContext(), "Δεν βρέθηκαν παρελθοντικά δεδομένα", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //Search The Weather For The Current City
        addTownList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String SearchTheCity = addTown.get(position).getCity();
                Intent intent = new Intent(WeatherDaily.this , MainActivity.class);
                intent.putExtra("SearchCity",SearchTheCity);
                startActivity(intent);
            }
        });

        addTownList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                icId = position;
                isCDeleted = DataBase.deleteCData(String.valueOf(icId),addTown.get(icId).getCity());
                if (isCDeleted > 0) {
                    Toast.makeText(getApplicationContext(), "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();
                    addTown.remove(icId);
                } else {
                    Toast.makeText(getApplicationContext(), "Δεν βρέθηκαν παρελθοντικά δεδομένα", Toast.LENGTH_SHORT).show();
                }
                adapterTown.notifyDataSetChanged();
                return true;
            }
        });

        viewCity(addTown);
        viewPData(dailyList);
    }

    public void mergePIconRows(int ID, int iconID){
        isPUpdated = DataBase.updatePIconID(ID,iconID);
        if(isPUpdated == false) {
            Toast.makeText(getApplicationContext(), "Σφάλμα κατά την ενημέρωση", Toast.LENGTH_SHORT).show();
        }
    }


    public void viewCity(ArrayList<CityAddList> list){
        cursor1 = DataBase.getCData();
        int i = 0;
        if(cursor1.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor1.moveToNext() ) {
                cId = Integer.valueOf(cursor1.getString(0));
                cCity = cursor1.getString(1);
                addPins1(list);
                mergeCIconRows(cId,icId+i);
                i++;
            }
            cursor1.close();
        }
   }

    public void mergeCIconRows(int ID, int iconID){
        isCUpdated = DataBase.updateCIconID(ID,iconID);
        if(isCUpdated == false) {
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
                mergePIconRows(Id,icPid+i);
                i++;
            }
            cursor.close();
        }
    }

    public void addPins(ArrayList<DailyList> list){
        perHour = new DailyList(Id,icPid,Time,MinTemp,MaxTemp,City,Summary);
        list.add(perHour);
    }
    public void addPins1(ArrayList<CityAddList> list){
        perHour1 = new CityAddList(cCity,icId);
        list.add(perHour1);
    }

}
