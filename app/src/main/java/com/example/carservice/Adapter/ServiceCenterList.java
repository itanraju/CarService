package com.example.carservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carservice.Model.ServiceCenterDetails;
import com.example.carservice.R;

import java.util.ArrayList;

public class ServiceCenterList extends RecyclerView.Adapter<ServiceCenterList.ViewHolder> {

    Context context;
    ArrayList<ServiceCenterDetails> serviceCenterLists;

    public ServiceCenterList(Context context, ArrayList<ServiceCenterDetails> serviceCenterLists) {
        this.context = context;
        this.serviceCenterLists = serviceCenterLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_center_layout,parent,false);
        return new ServiceCenterList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.serviceCenterName.setText(serviceCenterLists.get(position).getName());
        holder.address.setText(serviceCenterLists.get(position).getAddress());
        holder.contactNo.setText(serviceCenterLists.get(position).getMobileNo());
    }

    @Override
    public int getItemCount() {
        return serviceCenterLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView serviceCenterName,address,contactNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceCenterName=itemView.findViewById(R.id.serviceCenterName);
            address=itemView.findViewById(R.id.address);
            contactNo=itemView.findViewById(R.id.contactNo);
        }
    }
}
