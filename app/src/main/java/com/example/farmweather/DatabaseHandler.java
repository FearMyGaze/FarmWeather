package com.example.farmweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {

    //Version
    private static final int DATABASE_VERSION = 1;

    //DbName
    private static final String DATABASE_NAME = "WeaFa.db";

    //Tables
    private static final String TABLE_NAME = "Cities_Areas";

    //Columns
    private static final String RequestID = "RequestID";
    private static final String City = "City";
    private static final String CurTemp = "CurTemp";
    private static final String Weather = "Weather";
    private static final String Wind = "Wind";
    private static final String Humidity = "Humidity";
    private static final String SearchDate = "SearchDate";
    private static final String IconID = "IconID";


    SQLiteDatabase database;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+RequestID+" INTEGER PRIMARY KEY, "+City+" TEXT, "+CurTemp+" TEXT, " +
                ""+Weather+" TEXT, "+Wind+ " TEXT, "+Humidity+" TEXT, "+SearchDate+" TEXT, "+IconID+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String city,String date,String temp,String weather,String wind,String humidity){
        database = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(City,city);
        CV.put(SearchDate,date);
        CV.put(CurTemp,temp);
        CV.put(Weather,weather);
        CV.put(Wind,wind);
        CV.put(Humidity,humidity);
        long result = database.insert(TABLE_NAME,null ,CV);
        if(result == (-1)){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean updateIconID(int pKey,int item){
        database = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(IconID,item);
        long result = database.update(TABLE_NAME,CV,"RequestID = ?",new String[]{String.valueOf(pKey)});
        if(result == (-1)){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getData(String CITY,String sort){
        database = this.getWritableDatabase();
        String sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+CITY+"' ORDER BY RequestID "+sort+"";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }


    public Integer deleteDate(String rID,String area,String temp,String weather,String wind,String humidity,String date){
        database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"RequestID = ? AND City = ? AND CurTemp = ?" +
                " AND Weather = ? AND Wind = ? AND  Humidity = ? AND SearchDate = ? ",new String[] {rID,area,temp,weather,wind,humidity,date});
    }

    public Integer clearall(String area){
        database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"City = ?",new String[] {area});
    }

    public Cursor sortByWeatherStatus(String area,String weather,String sort){
        database = this.getWritableDatabase();
        String sql;
        if(area != "*"){
            sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND Weather = '"+weather+"' ORDER BY RequestID "+sort+"";
        }
        else
        {
            sql="SELECT * FROM Cities_Areas WHERE Weather = '"+weather+"' ORDER BY RequestID "+sort+"";

        }
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public Cursor sortByMonthStatus(String area,String date,String sort){
        database = this.getWritableDatabase();
        String sql;
        if(area != "*"){
            sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND SearchDate LIKE '%"+date+"%' ORDER BY RequestID "+sort+"";
        }
        else
        {
            sql="SELECT * FROM Cities_Areas WHERE SearchDate LIKE '%"+date+"%' ORDER BY RequestID "+sort+"";

        }
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public Cursor sortByTempStatus(String area,String temp,String sort){
        String sql;
        sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp = '"+temp+"' ORDER BY RequestID "+sort+"";
        switch (temp){
            case "1":
                if(area != "*"){
                    sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp BETWEEN 0 AND 10 ORDER BY RequestID "+sort+"";
                }
                else
                {
                    sql="SELECT * FROM Cities_Areas WHERE CurTemp BETWEEN 0 AND 10 ORDER BY RequestID "+sort+"";

                }
                break;
            case "2":
                if(area != "*"){
                    sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp BETWEEN 11 AND 25 ORDER BY RequestID "+sort+"";
                }
                else
                {
                    sql="SELECT * FROM Cities_Areas WHERE CurTemp BETWEEN 11 AND 25 ORDER BY RequestID "+sort+"";

                }                break;
            case "3":
                if(area != "*"){
                    sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp BETWEEN 26 AND 40 ORDER BY RequestID "+sort+"";
                }
                else
                {
                    sql="SELECT * FROM Cities_Areas WHERE CurTemp BETWEEN 26 AND 40 ORDER BY RequestID "+sort+"";

                }
                break;
            case "4":
                if(area != "*"){
                    sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp < 0 ORDER BY RequestID "+sort+"";
                }
                else
                {
                    sql="SELECT * FROM Cities_Areas WHERE CurTemp < 0 ORDER BY RequestID "+sort+"";

                }
                break;
            case "5":
                if(area != "*"){
                    sql="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+area+"' AND CurTemp > 40 ORDER BY RequestID "+sort+"";
                }
                else
                {
                    sql="SELECT * FROM Cities_Areas WHERE CurTemp > 40 ORDER BY RequestID "+sort+"";

                }
                break;
        }
        database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }
}