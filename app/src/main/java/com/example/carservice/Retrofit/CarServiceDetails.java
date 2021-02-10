package com.example.carservice.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CarServiceDetails {

    @GET("car_brands")
    Call<JsonObject> getCardBrands();

    @GET("fuelCities")
    Call<JsonObject> getCityList();

    @GET("car_service_centers")
    Call<JsonObject> getCarServiceDetails(@Query("brandId") String brandId, @Query("cityId") String cityId);
}
