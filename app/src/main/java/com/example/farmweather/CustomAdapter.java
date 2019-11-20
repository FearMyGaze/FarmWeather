package com.example.farmweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;


class CustomAdapter extends ArrayAdapter<WeatherList> {
    private Context mContext;
    int mResource;

    public CustomAdapter(Context context, int resource, ArrayList<WeatherList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        String date = getItem(position).getDate();
        String temp = getItem(position).getTemp();
        String weather = getItem(position).getWeather();
        String wind = getItem(position).getWind();
        String humidity = getItem(position).getHumidity();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent , false);

        TextView tvDate = convertView.findViewById(R.id.Date_adapter);
        TextView tvTemp=  convertView.findViewById(R.id.Temp_adapter);
        TextView tvWeather =  convertView.findViewById(R.id.Weather_adapter);
        TextView tvWind =  convertView.findViewById(R.id.Wind_adapter);
        TextView tvHumidity =  convertView.findViewById(R.id.Humidity_adapter);

        tvDate.setText(date);
        tvTemp.setText(temp);
        tvWeather.setText(weather);
        tvWind.setText(wind);
        tvHumidity.setText(humidity);

        return convertView;
    }
}
