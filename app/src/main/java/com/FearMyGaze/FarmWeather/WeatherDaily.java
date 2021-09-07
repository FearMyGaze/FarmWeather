package com.FearMyGaze.FarmWeather;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WeatherDaily extends AppCompatActivity {

    Boolean isPUpdated,isCUpdated,deletebug=true;
    Cursor cursor,cursor1,cursor2,cursor3;

    Integer switcher,Id=0,cId=0,icId=0,icPid = 0;
    String Time = "0",MinTemp = "0",MaxTemp = "0",Summary = "0",City = "0",sort = "DESC",month="*",searchcity="*",summary,type;
    String cCity="0";
    DailyList perHour;
    CityAddList perHour1;
    String API= "e1ddb97838fe7ea715475061978bb3b4"; //DarkSky
    DatabaseHandler DataBase = new DatabaseHandler(this);
    ArrayList<DailyList> balander;

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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(WeatherDaily.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Είστε σίγουρος;")
                        .setMessage("Πρόκειται να διαγραφεί αυτή η εγγραφή")
                        .setPositiveButton("NAI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            if(deletebug == true){
                                Integer deletedHistory = DataBase.deletePData(String.valueOf(dailyList.get(position).getId()),dailyList.get(position).getCity());
                                if (deletedHistory > 0) {
                                    Toast.makeText(getApplicationContext(), "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Σφάλμα κατά τη διαγραφή", Toast.LENGTH_SHORT).show();
                                }
                                dailyList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Παρακαλώ επαναφέρετε το ιστορικό στην αρχική του μορφή για να πραγματοποιήσετε τη διαγραφή", Toast.LENGTH_SHORT).show();
                            }
                            }
                        })
                        .setNegativeButton("OXI",null)
                        .show();
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(WeatherDaily.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Είστε σίγουρος;")
                        .setMessage("Πρόκειται να διαγραφεί αυτή η εγγραφή")
                        .setPositiveButton("NAI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Integer deleteTown = DataBase.deleteCData(String.valueOf(addTown.get(position).getCityID()),addTown.get(position).getCity());
                                        if (deleteTown > 0) {
                                            Toast.makeText(getApplicationContext(), "Η εγγραφή διαγράφτηκε", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Σφάλμα κατά τη διαγραφή", Toast.LENGTH_SHORT).show();
                                        }
                                        addTown.remove(position);
                                        adapterTown.notifyDataSetChanged();
                                    }
                                }
                        )
                        .setNegativeButton("ΟΧΙ",null)
                        .show();
                return true;
            }
        });
        viewCity(addTown);
        viewPData(dailyList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daily_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        balander = new ArrayList<>();
        final ListView list = findViewById(R.id.HistoryListView);
        final DailyAdapter adapter = new DailyAdapter(this,R.layout.adapter_daily,balander);

        switch (item.getItemId()){

            case R.id.HighMoth_1:
                month = "Jan";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_2:
                month = "Feb";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_3:
                month = "Mar";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_4:
                month = "Apr";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_5:
                month = "May";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_6:
                month = "Jun";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_7:
                month = "Jul";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_8:
                month = "Aug";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_9:
                month = "Sep";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_10:
                month = "Oct";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_11:
                month = "Nov";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HighMoth_12:
                month = "Dec";
                type="max";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_1:
                month = "Jan";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_2:
                month = "Feb";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_3:
                month = "Mar";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_4:
                month = "Apr";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_5:
                month = "May";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_6:
                month = "Jun";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_7:
                month = "Jul";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_8:
                month = "Aug";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_9:
                month = "Sep";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_10:
                month = "Oct";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_11:
                month = "Nov";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.LowMonth_12:
                month = "Dec";
                type="min";
                viewMaxMinTempData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_1:
                month = "Jan";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_2:
                month = "Feb";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_3:
                month = "Mar";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_4:
                month = "Apr";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_5:
                month = "May";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_6:
                month = "Jun";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_7:
                month = "Jul";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_8:
                month = "Aug";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_9:
                month = "Sep";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_10:
                month = "Oct";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_11:
                month = "Nov";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.HeatMonth_12:
                month = "Dec";
                summary = "Καύσωνας";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
                
            case R.id.RainMonth_1:
                month = "Jan";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_2:
                month = "Feb";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_3:
                month = "Mar";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_4:
                month = "Apr";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_5:
                month = "May";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_6:
                month = "Jun";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_7:
                month = "Jul";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_8:
                month = "Aug";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_9:
                month = "Sep";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_10:
                month = "Oct";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_11:
                month = "Nov";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.RainMonth_12:
                month = "Dec";
                summary = "Καταιγίδα";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_1:
                month = "Jan";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_2:
                month = "Feb";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_3:
                month = "Mar";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_4:
                month = "Apr";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_5:
                month = "May";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_6:
                month = "Jun";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_7:
                month = "Jul";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_8:
                month = "Aug";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_9:
                month = "Sep";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_10:
                month = "Oct";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_11:
                month = "Nov";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.SnowMonth_12:
                month = "Dec";
                summary = "Χιόνι";
                viewExtremeData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.DailyClear:
                viewPData(balander);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            default:return super.onOptionsItemSelected(item);

        }
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
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν καταχωρημένες πόλεις προς καταγραφή",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"Δεν υπάρχει καταγεγραμμένο ιστορικό",Toast.LENGTH_SHORT).show();
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
        deletebug = true;
    }


    public void viewExtremeData(ArrayList<DailyList> lista){
        int i=0;
        cursor2 = DataBase.extremeMonth(searchcity,month,summary,sort);
        if(cursor2.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            cursor2.moveToFirst();
                Id = Integer.valueOf(cursor2.getString(0));
                Time = cursor2.getString(2);
                MinTemp = cursor2.getString(3);
                MaxTemp = cursor2.getString(4);
                Summary = cursor2.getString(5);
                City = cursor2.getString(6);
                addPins(lista);
                mergePIconRows(Id,icPid+i);
                i++;
            }
            cursor2.close();
        deletebug = false;
    }

    public void viewMaxMinTempData(ArrayList<DailyList> lista){
        int i=0;
        cursor3 = DataBase.minMax(searchcity,month,type,sort);
        if(cursor3.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Δεν υπάρχουν δεδομένα για προβολή ",Toast.LENGTH_SHORT).show();
        }
        else{
            cursor3.moveToFirst();
                Id = Integer.valueOf(cursor3.getString(0));
                Time = cursor3.getString(2);
                MinTemp = cursor3.getString(3);
                MaxTemp = cursor3.getString(4);
                Summary = cursor3.getString(5);
                City = cursor3.getString(6);
                addPins(lista);
                mergePIconRows(Id,icPid+i);
                i++;

            cursor3.close();
        }
        deletebug = false;
    }

    public void addPins(ArrayList<DailyList> list){
        perHour = new DailyList(Id,icPid,Time,MinTemp,MaxTemp,City,Summary);
        list.add(perHour);
    }
    public void addPins1(ArrayList<CityAddList> list){
        perHour1 = new CityAddList(cId,cCity,icId);
        list.add(perHour1);
    }

}
