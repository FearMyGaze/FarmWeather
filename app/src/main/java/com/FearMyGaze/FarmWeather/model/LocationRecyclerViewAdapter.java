package com.FearMyGaze.FarmWeather.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FearMyGaze.FarmWeather.R;

import java.util.ArrayList;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<WeatherSnapshot> weatherSnapshotArrayList;

    public LocationRecyclerViewAdapter(ArrayList<WeatherSnapshot> m_weatherSnapshotArrayList){
        this.weatherSnapshotArrayList = m_weatherSnapshotArrayList;
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView m_RecyclerLocation,m_RecyclerMainTemperature,m_RecyclerWeatherDescription,m_RecyclerMinMaxTemperature;

        public MyViewHolder(final View view){
            super(view);
            m_RecyclerLocation = view.findViewById(R.id.RecyclerLocation);
            m_RecyclerMainTemperature = view.findViewById(R.id.RecyclerMainTemperature);
            m_RecyclerWeatherDescription = view.findViewById(R.id.RecyclerWeatherDescription);
            m_RecyclerMinMaxTemperature = view.findViewById(R.id.RecyclerMinMaxTemperature);
        }
    }

    @NonNull
    @Override
    public LocationRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View WeatherItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_locations,parent,false);
        return new MyViewHolder(WeatherItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerViewAdapter.MyViewHolder holder, int position) {
        String RecyclerLocation = weatherSnapshotArrayList.get(position).getAddress();
        String RecyclerMainTemperature = weatherSnapshotArrayList.get(position).getMainTemp();
        String RecyclerWeatherDescription = weatherSnapshotArrayList.get(position).getWeatherDescription();
        String RecyclerMinMaxTemperature = weatherSnapshotArrayList.get(position).getMaxMin();

        holder.m_RecyclerLocation.setText(RecyclerLocation);
        holder.m_RecyclerMainTemperature.setText(RecyclerMainTemperature);
        holder.m_RecyclerWeatherDescription.setText(RecyclerWeatherDescription);
        holder.m_RecyclerMinMaxTemperature.setText(RecyclerMinMaxTemperature);
    }

    @Override
    public int getItemCount() {
        return weatherSnapshotArrayList.size();
    }
}
