package com.example.carservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carservice.Adapter.ServiceCenterList;
import com.example.carservice.Model.ServiceCenterDetails;
import com.example.carservice.Retrofit.ApiClient;
import com.example.carservice.Retrofit.CarServiceDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCenterActivity extends AppCompatActivity {

    String cityId;
    ArrayList<ServiceCenterDetails> serviceCenterList=new ArrayList<>();
    RecyclerView serviceCenterRecycleView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);
        serviceCenterRecycleView=findViewById(R.id.serviceCenterRecycleView);
        cityId=getIntent().getStringExtra("cityId");
        progressBar=findViewById(R.id.progressBar3);
        Toast.makeText(this, SelectStateActivity.id +" "+cityId, Toast.LENGTH_SHORT).show();
        getServiceCenterData();
    }
    private void getServiceCenterData() {
        progressBar.setVisibility(View.VISIBLE);
        CarServiceDetails carServiceDetails= ApiClient.getClient().create(CarServiceDetails.class);
        Call<JsonObject> call=carServiceDetails.getCarServiceDetails(SelectStateActivity.id,cityId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray=jsonObj.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String id=jsonObject.getString("id");
                            String name=jsonObject.getString("name");
                            String address=jsonObject.getString("address");
                            String contactNo=jsonObject.getString("contactNo");

                            ServiceCenterDetails serviceCenterDetails=new ServiceCenterDetails();
                            serviceCenterDetails.setName(name);
                            serviceCenterDetails.setAddress(address);
                            serviceCenterDetails.setMobileNo(contactNo);

                            serviceCenterList.add(serviceCenterDetails);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ServiceCenterList serviceCenterList1=new ServiceCenterList(ServiceCenterActivity.this,serviceCenterList);
                    serviceCenterRecycleView.setAdapter(serviceCenterList1);
                    serviceCenterRecycleView.setLayoutManager(new LinearLayoutManager(ServiceCenterActivity.this));
                    serviceCenterRecycleView.setHasFixedSize(true);

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ServiceCenterActivity.this, "Resposnse Fail !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}