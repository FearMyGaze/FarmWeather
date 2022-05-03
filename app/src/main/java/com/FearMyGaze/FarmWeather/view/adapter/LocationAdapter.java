package com.FearMyGaze.FarmWeather.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.FearMyGaze.FarmWeather.R;
import com.FearMyGaze.FarmWeather.model.MiniWeatherSnapshot;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.view.activities.Locations;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private final ArrayList<MiniWeatherSnapshot> miniWeatherSnapshots;
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    private String language;

    public LocationAdapter(ArrayList<MiniWeatherSnapshot> miniWeatherSnapshots, Context context){
        LocationAdapter.context = context;
        this.miniWeatherSnapshots = miniWeatherSnapshots;
        miniWeatherSnapshots.addAll(WeatherSnapshotDatabase.getInstance(context).locationWeatherSnapshotDAO().getAllLocationWeatherSnapshots());
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshMiniWeatherSnapshotsList() {
        miniWeatherSnapshots.clear();
        miniWeatherSnapshots.addAll(WeatherSnapshotDatabase.getInstance(context).locationWeatherSnapshotDAO().getAllLocationWeatherSnapshots());
        notifyDataSetChanged();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView m_RecyclerLocation,m_RecyclerMainTemperature,m_RecyclerWeatherDescription,m_RecyclerMinMaxTemperature;
        ConstraintLayout m_constraintLayout;
        public MyViewHolder(final View view){
            super(view);
            m_RecyclerLocation = view.findViewById(R.id.RecyclerLocation);
            m_RecyclerMainTemperature = view.findViewById(R.id.RecyclerMainTemperature);
            m_RecyclerWeatherDescription = view.findViewById(R.id.RecyclerWeatherDescription);
            m_RecyclerMinMaxTemperature = view.findViewById(R.id.RecyclerMinMaxTemperature);
            m_constraintLayout = view.findViewById(R.id.item_recyclerview_locations);
        }
    }

    @NonNull
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View WeatherItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_locations,parent,false);
        return new MyViewHolder(WeatherItemView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.MyViewHolder holder, int position) {
        String RecyclerLocation = miniWeatherSnapshots.get(position).locationTitle;
        String RecyclerMainTemperature = String.valueOf((int) miniWeatherSnapshots.get(position).temp);
        String RecyclerWeatherDescription = miniWeatherSnapshots.get(position).description;
        String RecyclerMinTemperature = (int) miniWeatherSnapshots.get(position).min + "℃";
        String RecyclerMaxTemperature = (int) miniWeatherSnapshots.get(position).max + "℃";

        holder.m_RecyclerLocation.setText(RecyclerLocation);
        holder.m_RecyclerMainTemperature.setText(RecyclerMainTemperature);
        holder.m_RecyclerWeatherDescription.setText(RecyclerWeatherDescription);
        holder.m_RecyclerMinMaxTemperature.setText(String.format("%s/%s", RecyclerMinTemperature, RecyclerMaxTemperature));

        holder.m_constraintLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, Locations.class);
            intent.putExtra("location", miniWeatherSnapshots.get(position).locationTitle);
            intent.putExtra("language", language);
            context.startActivity(intent);
        });

        holder.m_constraintLayout.setOnLongClickListener(view -> {
            WeatherSnapshotDatabase.getInstance(context).locationWeatherSnapshotDAO().deleteMiniWeatherSnapshot(miniWeatherSnapshots.get(position));
            miniWeatherSnapshots.remove(position);
            notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return miniWeatherSnapshots.size();
    }

    public void addNewMiniWeatherSnapshot(MiniWeatherSnapshot miniWeatherSnapshot){
        WeatherSnapshotDatabase.getInstance(context).locationWeatherSnapshotDAO().insertNewMiniWeatherSnapshot(miniWeatherSnapshot);
        miniWeatherSnapshots.add(miniWeatherSnapshot);
    }
}