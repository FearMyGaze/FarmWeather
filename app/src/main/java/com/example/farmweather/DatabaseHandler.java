package com.example.farmweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


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

    public boolean insertData(String Ci,String Date,String Temp,String Wea,String Wi,String Hu){
        database = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(City,Ci);
        CV.put(SearchDate,Date);
        CV.put(CurTemp,Temp);
        CV.put(Weather,Wea);
        CV.put(Wind,Wi);
        CV.put(Humidity,Hu);
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

    public Cursor getData(String CITY){
        database = this.getWritableDatabase();
        String sql0="SELECT * FROM Cities_Areas WHERE Cities_Areas.City LIKE '"+CITY+"' ORDER BY RequestID DESC";
        Cursor cursor = database.rawQuery(sql0,null);
        return cursor;
    }


    public Integer deleteDate(String rID,String area,String temp,String weather,String wind,String humidity,String date){
        database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"RequestID = ? AND City = ? AND CurTemp = ?" +
                " AND Weather = ? AND Wind = ? AND  Humidity = ? AND SearchDate = ? ",new String[] {rID,area,temp,weather,wind,humidity,date});
    }
//"City = '"+area+"' AND SearchDate = '"+date+"' AND CurTemp = '"+temp+"' AND Weather = '"+we+"' AND Wind = '"+wi+"' AND  Humidity = '"+Hu+"'"
}

   /* public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("/data/data/com.example.farmweather/databases/WeaFa.db", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            //Error DB does not exist restart the app to create the DB
        }
        return checkDB != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkDataBase() == true) {
            DatabaseHandler databaseHandler = new DatabaseHandler(this);
        }
    }*/