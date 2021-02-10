package com.example.carservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carservice.Model.CityDetailModel;
import com.example.carservice.R;
import com.example.carservice.ServiceCenterActivity;

import java.util.ArrayList;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    Context context;
    ArrayList<CityDetailModel> cityList;

    public CityListAdapter(Context context, ArrayList<CityDetailModel> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_layout,parent,false);
        return new CityListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.cityName.setText(cityList.get(position).getCityName());
       holder.cityName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, ServiceCenterActivity.class);
               intent.putExtra("cityId",cityList.get(position).getId());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView cityName;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cityName=itemView.findViewById(R.id.cityName);
            }
        }
}
