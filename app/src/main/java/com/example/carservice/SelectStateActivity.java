package com.example.carservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carservice.Adapter.CityListAdapter;
import com.example.carservice.Model.CityDetailModel;
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

public class SelectStateActivity extends AppCompatActivity {

    public static String id,name;
    TextView toolBar_name;
    RecyclerView stateLIstRecycleView;
    Spinner stateListSpinner;
    ProgressBar progressBar;
    ArrayList<String> stateList=new ArrayList<>();
    ArrayList<CityDetailModel> cityList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_state);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        stateLIstRecycleView = findViewById(R.id.stateListRecycleView);
        stateListSpinner = findViewById(R.id.stateListSpinner);
        progressBar=findViewById(R.id.progressBar2);
        toolBar_name=findViewById(R.id.toolbar_brandName);
        toolBar_name.setText(name+" "+id);

        getStateList();
    }

    private void getStateList() {
        progressBar.setVisibility(View.VISIBLE);
        CarServiceDetails carServiceDetails= ApiClient.getClient().create(CarServiceDetails.class);
        Call<JsonObject> call=carServiceDetails.getCityList();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response.body()));

                        JSONArray arraystateList = jsonObj.getJSONArray("data");
                        for (int i = 0; i < arraystateList.length(); i++) {
                            JSONObject jsonObject = arraystateList.getJSONObject(i);
                            stateList.add(jsonObject.getString("name"));

                        }

                        ArrayAdapter ad = new ArrayAdapter(SelectStateActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stateListSpinner.setAdapter(ad);

                        progressBar.setVisibility(View.GONE);
                        stateListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cityList.clear();
                                try {

                                    JSONArray arraystateList = jsonObj.getJSONArray("data");
                                    for (int i = 0; i < arraystateList.length(); i++) {

                                        JSONObject jsonObject = arraystateList.getJSONObject(i);

                                        JSONArray arrayCityList = jsonObject.getJSONArray("cityList");

                                        for (int j = 0; j < arrayCityList.length(); j++) {

                                            JSONObject jsonObject1 = arrayCityList.getJSONObject(j);

                                            if(stateListSpinner.getSelectedItem().equals(jsonObject1.getString("stateName")))
                                            {
                                                /*cityList.add(jsonObject1.getString("cityName"));
                                                idList.add(jsonObject1.getString("id"));*/
                                                String cityId=jsonObject1.getString("id");
                                                String cityName=jsonObject1.getString("cityName");
                                                CityDetailModel cityDetailModel=new CityDetailModel();
                                                cityDetailModel.setId(cityId);
                                                cityDetailModel.setCityName(cityName);
                                                cityList.add(cityDetailModel);
                                            }
                                            CityListAdapter cityListAdapter=new CityListAdapter(SelectStateActivity.this,cityList);
                                            stateLIstRecycleView.setAdapter(cityListAdapter);
                                            stateLIstRecycleView.setLayoutManager(new LinearLayoutManager(SelectStateActivity.this));
                                            stateLIstRecycleView.setHasFixedSize(true);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SelectStateActivity.this, "Response Fail !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}