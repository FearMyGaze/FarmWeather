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
import com.FearMyGaze.FarmWeather.model.WeatherModel;
import com.FearMyGaze.FarmWeather.repository.WeatherSnapshotDatabase;
import com.FearMyGaze.FarmWeather.view.activities.Locations;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private final List<WeatherModel> weatherModels;
//    private final ItemClickListener clickListener;
//    private final ItemOnLongClickListener longClickListener;


    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public LocationAdapter(List<WeatherModel> weatherModels, Context context){
        this.weatherModels = weatherModels;
//        this.clickListener = clickListener;
//        this.longClickListener = longClickListener;
        LocationAdapter.context = context;
        weatherModels.addAll(WeatherSnapshotDatabase.getInstance(context).weatherDAO().getAllWeathers());
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
        String RecyclerLocation = weatherModels.get(position).getLocation();
        String RecyclerMainTemperature = String.valueOf((int) weatherModels.get(position).getMainTemp());
        String RecyclerWeatherDescription = weatherModels.get(position).getWeatherDescription();
        String RecyclerMinTemperature = (int) weatherModels.get(position).getMainTempMin() + "℃";
        String RecyclerMaxTemperature = (int) weatherModels.get(position).getMainTempMax() + "℃";

        holder.m_RecyclerLocation.setText(RecyclerLocation);
        holder.m_RecyclerMainTemperature.setText(RecyclerMainTemperature);
        holder.m_RecyclerWeatherDescription.setText(RecyclerWeatherDescription);
        holder.m_RecyclerMinMaxTemperature.setText(String.format("%s/%s", RecyclerMinTemperature, RecyclerMaxTemperature));

        holder.m_constraintLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, Locations.class);
            intent.putExtra("location", weatherModels.get(position).getLocation());
            context.startActivity(intent);
        });

        holder.m_constraintLayout.setOnLongClickListener(view -> {
            WeatherSnapshotDatabase.getInstance(context).weatherDAO().deleteWeatherModel(weatherModels.get(position));
            weatherModels.remove(position);
            notifyDataSetChanged();
            return true;
        });
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

    public interface ItemClickListener{
        void onClick(LocationAdapter locationAdapter);
    }

    public interface ItemOnLongClickListener{
        void onLongClick(LocationAdapter locationAdapter);
    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshMiniWeatherSnapshotsList() {
        weatherModels.clear();
        weatherModels.addAll(WeatherSnapshotDatabase.getInstance(context).weatherDAO().getAllWeathers());
        notifyDataSetChanged();
    }

    public void addNewMiniWeatherSnapshot(WeatherModel weatherModel){
        WeatherSnapshotDatabase.getInstance(context).weatherDAO().insertWeatherModel(weatherModel);
        weatherModels.add(weatherModel);
    }
}
