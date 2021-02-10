package com.example.carservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carservice.Adapter.CarBrandAdapter;
import com.example.carservice.Model.CarBrandModel;
import com.example.carservice.Retrofit.ApiClient;
import com.example.carservice.Retrofit.CarServiceDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<CarBrandModel> carBrandModelArrayList=new ArrayList<>();
    ProgressBar progressBar;
    RecyclerView brandRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindView();
        getCarBrandList();
    }
    private void BindView() {
        progressBar=findViewById(R.id.progressBar);
        brandRecycleView=findViewById(R.id.brandRecycleView);
    }

    private void getCarBrandList() {
        progressBar.setVisibility(View.VISIBLE);
        CarServiceDetails carServiceDetails= ApiClient.getClient().create(CarServiceDetails.class);
        Call<JsonObject> call=carServiceDetails.getCardBrands();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray=jsonObj.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id=jsonObject.getString("id");
                            String brandName=jsonObject.getString("brandName");
                            String imageUrl=jsonObject.getString("imageUrl");

                            CarBrandModel carBrandModel=new CarBrandModel();
                            carBrandModel.setId(id);
                            carBrandModel.setBrandName(brandName);
                            carBrandModel.setImageUrl(imageUrl);

                            carBrandModelArrayList.add(carBrandModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CarBrandAdapter carBrandAdapter=new CarBrandAdapter(MainActivity.this,carBrandModelArrayList);
                    brandRecycleView.setAdapter(carBrandAdapter);
                    brandRecycleView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                    brandRecycleView.setHasFixedSize(true);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Response Fail !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}