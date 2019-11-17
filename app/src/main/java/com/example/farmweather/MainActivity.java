package com.example.farmweather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    DatabaseHandler DB = new DatabaseHandler(this);

    //DHLWSH METABLHTWN GIA PERISOTERES PLHROFORIES
    String LowUV = "Χαμηλός";
    String HighUV = "Υψηλός";
    String UV = "Κανονικός";
    String LowRain = "Ελάχιστη";
    String HighRain = "Μέγιστη";
    String Rain = "Καθόλου";
    String Animals = "Καμιά";
    String DangerAnimals = "Κίνδυνος";
    String SafeDrive = "Καμιά";
    String RiskDrive = "Προσοχή";
    String WaterPlant = "Όχι";
    String DryPlant = "Ναι";
    String Plant = "Κανονικό";

    //DHLWSH METABLHTWN GIA ELEXO KAIROU
    String Clear = "Καθαρός";
    String RainSky = "Βροχερός";
    String Storm = "Καταιγίδα";
    String Sun = "Λιακάδα";
    String Heat = "Καύσωνας";
    String Snow = "Χιόνι";

    //ALLES DHLWSEIS METABLHTWN
    String API = "360443d882c3a8260a2d10ba6a086b9f";
    String WeatherGR;
    int maxTemp;
    Double degree;


    //ANOIGMA DEUTERHS FORMAS
    private void openActivity2() {
        Intent intent = new Intent(this,info_activity.class);
        startActivity(intent);
    }


    //DHLWSH ANTIKHMENWN GIA JSON
    TextView JLocation,JTime,JTemp,JTemp_min,JTemp_max,JSunrise,JSunset,JWind_speed,JWind_Deg,JPressure,JHumidity,JStatus;
    TextView Town,Weather,TextForUV,TextForRain,TextForAnimals,TextForDrive,TextForPlants,Temperature,CelsiusIcon,Feel;
    ImageView ImageWeather;
    ImageView Row_Add;
    int currentHour;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PAIRNEI THN WRA
        Calendar rightNow = Calendar.getInstance();
        currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        //DHLWSH JASON ANTIKEIMENA
        Row_Add = findViewById(R.id.Row_add);
        JLocation = findViewById(R.id.location);
        JTime = findViewById(R.id.updated_time);
        JTemp = findViewById(R.id.temperature);
        JTemp_min = findViewById(R.id.min_temperature);
        JTemp_max = findViewById(R.id.max_temperature);
        JSunrise = findViewById(R.id.sunrise);
        JSunset = findViewById(R.id.sunset);
        JWind_speed = findViewById(R.id.wind);
        JWind_Deg = findViewById(R.id.wind_direct);
        JPressure = findViewById(R.id.pressure);
        JHumidity = findViewById(R.id.humidity);
        JStatus = findViewById(R.id.status);


        //DHLWSH ANTIKEIMENWN STO LAYOUT GIA PLHROFORIES
        TextForUV = findViewById(R.id.uv_index);
        TextForRain = findViewById(R.id.rain_prob);
        TextForAnimals = findViewById(R.id.animal);
        TextForDrive = findViewById(R.id.drive);
        TextForPlants = findViewById(R.id.water_plants);

        //ALLES DHLWSEIS ANTIKEIMEWN
        ImageWeather = findViewById(R.id.status_pic);
        Temperature = findViewById(R.id.temperature);
        CelsiusIcon = findViewById(R.id.temperatureC);
        final SwipeRefreshLayout Swipe = findViewById(R.id.refresh);
        final TextView info = findViewById(R.id.info); // Lamps

        //ALLES DHLWSEIS ANTIKEIMENWN
        final TextView list_town = findViewById(R.id.list_town);
        final TextView TempMin = findViewById(R.id.min_temperature);
        final TextView TempMax = findViewById(R.id.max_temperature);

        Town = findViewById(R.id.Town);
        Weather = findViewById(R.id.status);
        Feel = findViewById(R.id.temp_feel);



        Town.setText(getIntent().getStringExtra("Town"));

        //KWDIKAS GIA ELEXOUS PLHROFORIWN
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { //KWDIKAS SWIPE
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh() {
                Swipe.setRefreshing(false);
                list_town.setText("Για "+JLocation.getText().toString());
                new weatherTask().execute();


                //APO EDW KAI KATW GIA PROSTHKH KWDIKA STO SWIPE


            }
        });//TELOS KWDIKA SWIPE

        list_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Old_Weather.class);
                startActivity(intent);
            }
        });

        Temperature.setOnClickListener(new View.OnClickListener() { //KWDIKAS GIA METATROPH BATHMWN C & F
            @Override
            public void onClick(View v) {
                String symbol , value , lastvalue , min , max , feel , lastfeel , lastmin , lastmax;
                double answer , minimum , maximum , feeling;
                float floatans , floatmin , floatmax , floatfeel;
                min = TempMin.getText().toString();
                max = TempMax.getText().toString();
                feel = Feel.getText().toString();
                symbol = CelsiusIcon.getText().toString();
                value = Temperature.getText().toString();
                answer = Double.parseDouble(value);
                minimum = Double.parseDouble(min);
                maximum = Double.parseDouble(max);
                feeling = Double.parseDouble(feel);
                floatmin = (float) minimum;
                floatmax = (float) maximum;
                floatans = (float) answer;
                floatfeel = (float) feeling;

                switch (symbol){
                    case "C":
                        lastvalue = String.valueOf(Converter.convertCelsiusToFahrenheit(floatans));
                        lastmin = String.valueOf(Converter.convertCelsiusToFahrenheit(floatmin));
                        lastmax = String.valueOf(Converter.convertCelsiusToFahrenheit(floatmax));
                        lastfeel = String.valueOf(Converter.convertCelsiusToFahrenheit(floatfeel));
                        Temperature.setText(lastvalue);
                        TempMin.setText(lastmin);
                        TempMax.setText(lastmax);
                        Feel.setText(lastfeel);
                        CelsiusIcon.setText("F");
                        break;

                    case "F":
                        lastvalue = String.format("%.0f",Converter.convertFahrenheitToCelsius(floatans));
                        lastmin = String.format("%.0f",Converter.convertFahrenheitToCelsius(floatmin));
                        lastmax = String.format("%.0f", Converter.convertFahrenheitToCelsius(floatmax));
                        lastfeel = String.format("%.0f", Converter.convertFahrenheitToCelsius(floatfeel));
                        Temperature.setText(lastvalue);
                        TempMin.setText(lastmin);
                        TempMax.setText(lastmax);
                        Feel.setText(lastfeel);
                        CelsiusIcon.setText("C");
                        break;
                }

            }
        }); //TELOS KWDIKA METATROPHS

        //KALESMA SYNARTHSHS GIA ANOIGMA FORMAS
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        list_town.setText("Για "+Town.getText().toString().substring(0,1).toUpperCase()+ Town.getText().toString().substring(1));
        new weatherTask().execute();

        addData();
    }//TELOS ONCREATE

    public void addData(){
        Row_Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = DB.insertData(JTime.getText().toString(), JTemp.getText().toString(),Weather.getText().toString(), JWind_speed.getText().toString(), JHumidity.getText().toString());
                        if (isInserted == false){
                            Toast.makeText(MainActivity.this, "Data cannot be inserted ", Toast.LENGTH_LONG).show();                        }
                    }
                }
        );
    }

    class weatherTask extends AsyncTask<String, Void, String> {

        String City = Town.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("PreExecute");
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + City + "&units=metric&appid=" + API);
            return response;
        }

        //DHLWSH ANTIKEIMENWN TYPOU JASON
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String Time = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp");
                String weatherDescription = weather.getString("main");
                String tempMin =  main.getString("temp_min");
                String tempMax =  main.getString("temp_max");
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String windDeg = wind.getString("deg");
                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                //METATROPH THERMOKRASIWN SE FLOAT GIA NA TA METATREPSOUME SE AKERAIOUS
                double tempD = Double.parseDouble(temp);
                double minD = Double.parseDouble(tempMin);
                double maxD = Double.parseDouble(tempMax);
                float tempF = (float) tempD;
                float minF = (float) minD;
                float maxF = (float) maxD;


                //EMFANISH TWN JSON ANTIKEIMENWN STA ANTIKEIMENA MAS
                JLocation.setText(address);
                JTime.setText(Time);
                JTemp.setText(String.format("%.0f",tempF));
                JTemp_min.setText(String.format("%.0f",minF));
                JTemp_max.setText(String.format("%.0f",maxF));
                JSunrise.setText(new SimpleDateFormat("hh:mm", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                JSunset.setText(new SimpleDateFormat("k:mm ", Locale.ENGLISH).format(new Date(sunset * 1000)));
                JWind_speed.setText(windSpeed+"m/s");
                JWind_Deg.setText(windDeg);
                JPressure.setText(pressure+"hPa");
                JHumidity.setText(humidity+"%");
                JStatus.setText(weatherDescription);

                //METABLHTH GIA METATROPH KAIROU APO AGGLIKA SE ELLHNIKA
                WeatherGR = weatherDescription;

                System.out.println("OnPostExecute");

            } catch (JSONException e) {
                System.out.println(e);
            }

            //KWDIKAS GIA YPOLOGISMOS KATEYTHYNSH AERA!
            degree = Double.parseDouble(JWind_Deg.getText().toString());
            if (degree>337.5) JWind_Deg.setText("Βόρεια");
            else if (degree>292.5) JWind_Deg.setText("ΒΔ");
            else if(degree>247.5) JWind_Deg.setText("Δυτικά");
            else if(degree>202.5) JWind_Deg.setText("ΝΔ");
            else if(degree>157.5) JWind_Deg.setText("Νότια");
            else if(degree>122.5) JWind_Deg.setText("NA");
            else if(degree>67.5) JWind_Deg.setText("Ανατολικά");
            else if(degree>22.5)JWind_Deg.setText("ΒΑ");


            //METATROPH KAIROU APO AGGLIKA SE ELLHNIKA

            if(WeatherGR.equals("Clouds")){
                Weather.setText(Clear);
            }
            if(WeatherGR.equals("Drizzle") ||
                    WeatherGR.equals("Rain")){
                Weather.setText(RainSky);
            }
            if(WeatherGR.equals("Thunderstorm")){
                Weather.setText(Storm);
            }
            if((WeatherGR.equals("Clear") ||
                    WeatherGR.equals("Mist") ||
                    WeatherGR.equals("Fog") ||
                    WeatherGR.equals("Haze")) && (currentHour >= 19 || currentHour < 6)){
                Weather.setText(Clear);
            }
            if((WeatherGR.equals("Clear") ||
                    WeatherGR.equals("Mist")||
                    WeatherGR.equals("Fog") ||
                    WeatherGR.equals("Haze"))&& (currentHour >= 6 && currentHour < 19)) {
                Weather.setText(Sun);
            }
            if(WeatherGR.equals("Snow")){
                Weather.setText(Snow);
            }
            //METATROPH THERMOKRASIAS SE AKERAIO GIA XRHSH KAYSWNA
            maxTemp = Integer.parseInt(Temperature.getText().toString());
            if(maxTemp >= 30 && CelsiusIcon.getText().toString() == "C"){
                Weather.setText(Heat);
            }
            if(maxTemp >=86 && CelsiusIcon.getText().toString() == "F"){
                Weather.setText(Heat);
            }



            //KWDIKAS GIA HLIO
            if (Weather.getText().toString() == Sun) {
                TextForUV.setText(UV);
                TextForRain.setText(LowRain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(SafeDrive);
                TextForPlants.setText(Plant);
                String Feels = String.valueOf(maxTemp+2);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.sunny));
            }
            //KWDIKAS GIA KATHARO BRADY
            if (Weather.getText().toString() == Clear && (currentHour >= 19 || currentHour < 6)) {
                TextForUV.setText(LowUV);
                TextForRain.setText(Rain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(SafeDrive);
                TextForPlants.setText(Plant);
                String Feels = String.valueOf(maxTemp-1);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.moon));
            }
            //KWDIKAS GIA KATHARO PRWI
            if (Weather.getText().toString() == Clear && (currentHour >= 6 && currentHour < 19)) {
                TextForUV.setText(LowUV);
                TextForRain.setText(Rain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(SafeDrive);
                TextForPlants.setText(Plant);
                String Feels = String.valueOf(maxTemp+1);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.clear));

            }
            //KWDIKAS GIA KAYSWNNA
            if (Weather.getText().toString() == Heat) {
                TextForUV.setText(HighUV);
                TextForRain.setText(Rain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(SafeDrive);
                TextForPlants.setText(DryPlant);
                String Feels = String.valueOf(maxTemp);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.sunny));
            }
            //KWDIKAS GIA KATAIGIDA BRADU
            if (Weather.getText().toString() == Storm && (currentHour >= 19 || currentHour < 6)) {
                TextForUV.setText(LowUV);
                TextForRain.setText(HighRain);
                TextForAnimals.setText(DangerAnimals);
                TextForDrive.setText(RiskDrive);
                TextForPlants.setText(WaterPlant);
                String Feels = String.valueOf(maxTemp-1);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.night_storm));
            }

            //KWDIKAS GIA KATAGIDA PRWI
            if (Weather.getText().toString() == Storm && (currentHour >= 6 && currentHour < 19)) {
                TextForUV.setText(LowUV);
                TextForRain.setText(HighRain);
                TextForAnimals.setText(DangerAnimals);
                TextForDrive.setText(RiskDrive);
                TextForPlants.setText(WaterPlant);
                String Feels = String.valueOf(maxTemp-1);
                Feel.setText(Feels);;
                ImageWeather.setImageDrawable(getDrawable(R.drawable.storm));
            }
            //KWDIKAS GIA BROXH BRADU
            if (Weather.getText().toString() == RainSky && (currentHour >= 19 || currentHour < 6))
            {
                TextForUV.setText(LowUV);
                TextForRain.setText(HighRain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(RiskDrive);
                TextForPlants.setText(WaterPlant);
                String Feels = String.valueOf(maxTemp-1);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.night_rain));
            }
            //KWDIKAS GIA BROXH PRWI
            if (Weather.getText().toString() == RainSky && (currentHour >= 6 && currentHour < 19))
            {
                TextForUV.setText(LowUV);
                TextForRain.setText(HighRain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(RiskDrive);
                TextForPlants.setText(WaterPlant);
                String Feels = String.valueOf(maxTemp+1);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.rain));
            }
            //KWDIKAS GIA XIONI
            if(Weather.getText().toString() == Snow){
                TextForUV.setText(LowUV);
                TextForRain.setText(Rain);
                TextForAnimals.setText(Animals);
                TextForDrive.setText(RiskDrive);
                TextForPlants.setText(Plant);
                String Feels = String.valueOf(maxTemp-2);
                Feel.setText(Feels);
                ImageWeather.setImageDrawable(getDrawable(R.drawable.snowing));
            }

        }

    }

}