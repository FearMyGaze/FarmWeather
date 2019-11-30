package com.example.farmweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class DailyAdapter extends ArrayAdapter<DailyList> {

    private Context mContext;
    int mResource;

    public DailyAdapter(Context context, int resource, ArrayList<DailyList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String time = getItem(position).getTime();
        String tempMax = getItem(position).getTempMax();
        String tempMin = getItem(position).getTempMin();
        String summary = getItem(position).getSummary();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent , false);

        TextView tvTime = convertView.findViewById(R.id.DailyTime);
        TextView tvTempMax=  convertView.findViewById(R.id.DailyMax);
        TextView tvTempMin =  convertView.findViewById(R.id.DailyMin);
        TextView tvTown =  convertView.findViewById(R.id.DailyTown);

        tvTime.setText(time);
        tvTempMax.setText(tempMax);
        tvTempMin.setText(tempMin);
        tvTown.setText(summary);

        return convertView;
    }

}
