package com.example.carservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carservice.Model.CarBrandModel;
import com.example.carservice.R;
import com.example.carservice.SelectStateActivity;

import java.util.ArrayList;

public class CarBrandAdapter extends RecyclerView.Adapter<CarBrandAdapter.ViewHolder> {

    Context context;
    ArrayList<CarBrandModel> carBrandModelArrayList;

    public CarBrandAdapter(Context context, ArrayList<CarBrandModel> carBrandModelArrayList) {
        this.context = context;
        this.carBrandModelArrayList = carBrandModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_brand_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tBrandName.setText(carBrandModelArrayList.get(position).getBrandName());
        String id= String.valueOf(carBrandModelArrayList.get(position).getId());
        Glide.with(context).load(carBrandModelArrayList.get(position).getImageUrl()).placeholder(R.drawable.car_place_holder).into(holder.iBrandImage);
        holder.iBrandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectStateActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",carBrandModelArrayList.get(position).getBrandName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carBrandModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tBrandName;
        ImageView iBrandImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tBrandName=itemView.findViewById(R.id.brandName);
            iBrandImage=itemView.findViewById(R.id.brandImage);
        }
    }
}
