package com.example.goeco_amazon.restService;

import com.example.goeco_amazon.models.DeliveryModel;
import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.models.UpdateUser;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.DeliveryResponse;
import com.example.goeco_amazon.responsemodels.GetDeliveryResponse;
import com.example.goeco_amazon.responsemodels.GetLeaderboardResponse;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.responsemodels.ProductResponse;
import com.example.goeco_amazon.responsemodels.ProfileResponse;
import com.example.goeco_amazon.responsemodels.RegisterResponse;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.responsemodels.UpdateUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("register")
    Call<RegisterResponse> postregister(@Body UserModel userModel);

    @POST("login")
    Call<LoginResponse> postlogin(@Body UserLogin userLogin);

    @POST("delivery")
    Call<DeliveryResponse> adddelivery(@Body DeliveryModel deliveryModel);

    @POST("user/update")
    Call<UpdateUserResponse> updateuser(@Body UpdateUser updateUser);

    @GET("getprofile/{id}")
    Call<ProfileResponse> getprofile(@Path("id") String id);

    @GET("deliveries/{userId}")
    Call<GetDeliveryResponse> getdeliveries(@Path("userId") String userId);

    @GET("leaderboard")
    Call<GetLeaderboardResponse> getleaderboard(@Query("userId") String userId);


    @PUT("delivery/{deliveryid}")
    Call<UpdateDeliveryResponse> updatedelivery(@Path("deliveryid") String deliveryid,@Body UpdateDelivery updateDelivery);

    @GET("nearby-pickups/{lat}/{lon}")
    Call<List<PickupPointModel>> getpickuppoints(@Path("lat") double lat, @Path("lon") double lon);

    @GET("getmetrics")
    Call<List<MetricsData>> getmetrics(@Query("userDistance") double userDistance, @Query("userWeight") int userWeight);

    @GET("products")
    Call<ProductResponse> getproducts();


}
