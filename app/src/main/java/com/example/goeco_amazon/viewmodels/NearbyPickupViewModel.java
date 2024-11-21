package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPickupViewModel extends ViewModel {
    private MutableLiveData<NearbyPickupResponse> userData=new MutableLiveData<>();
    public LiveData<NearbyPickupResponse> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void nearbypickup(Application application,double lat,double lon){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getpickuppoints(loginManager.gettoken(),lat,lon).enqueue(new Callback<NearbyPickupResponse>() {
            @Override
            public void onResponse(Call<NearbyPickupResponse> call, Response<NearbyPickupResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<NearbyPickupResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
