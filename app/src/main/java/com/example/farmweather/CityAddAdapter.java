package com.example.farmweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

class CityAddAdapter extends ArrayAdapter<CityAddList> {

    private Context mContext;
    int mResource;

    public CityAddAdapter(Context context, int resource, ArrayList<CityAddList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String city = getItem(position).getCity();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvCity = convertView.findViewById(R.id.AddCity);

        tvCity.setText(city);
        return convertView;
    }
}