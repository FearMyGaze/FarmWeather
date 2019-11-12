package com.example.farmweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



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
    private static final String Area = "Area";
    private static final String MinTemp = "MinTemp";
    private static final String MaxTemp = "MaxTemp";
    private static final String CurTemp = "CurTemp";
    private static final String Weather = "Weather";
    private static final String Wind = "Wind";
    private static final String Pressure = "Pressure";
    private static final String Humidity = "Humidity";
    private static final String TimeDate = "TimeDate";
    private static final String SearchDate = "SearchDate";


    SQLiteDatabase database;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+RequestID+" INTEGER PRIMARY KEY, "+City+"" +
                " TEXT, "+MinTemp+" TEXT, "+MaxTemp+" TEXT, "+CurTemp+" TEXT, "+Weather+" TEXT, "+Wind+" TEXT, "+Pressure+"" +
                " TEXT, "+Humidity+" TEXT, "+TimeDate+" TEXT, "+Area+" TEXT, "+SearchDate+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   public boolean insertData(String Date,String Temp,String Wea,String Wi,String Hu){
        database = getWritableDatabase();
        ContentValues CV = new ContentValues();
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