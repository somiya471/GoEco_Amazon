package com.example.goeco_amazon.restService;

import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.responsemodels.ProductResponse;
import com.example.goeco_amazon.responsemodels.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("register")
    Call<RegisterResponse> postregister(@Body UserModel userModel);

    @POST("login")
    Call<LoginResponse> postlogin(@Body UserLogin userLogin);

    @GET("nearby-pickups/{lat}/{lon}")
    Call<NearbyPickupResponse> getpickuppoints(@Header("Authorization") String Token, @Path("job_id") double lat, @Path("job_id") double lon);

    @GET("getmetrics")
    Call<MetricsResponse> getmetrics(@Header("Authorization") String Token, @Body MetricsModel metricsModel);

    @GET("products")
    Call<ProductResponse> getproducts(@Header("Authorization") String Token);

}
