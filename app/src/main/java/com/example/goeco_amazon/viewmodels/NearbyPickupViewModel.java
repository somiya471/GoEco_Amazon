package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyPickupViewModel extends ViewModel {
    private MutableLiveData<List<PickupPointModel>> userData=new MutableLiveData<>();
    public LiveData<List<PickupPointModel>> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void nearbypickup(Application application,double lat,double lon){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getpickuppoints(lat,lon).enqueue(new Callback<List<PickupPointModel>>() {
            @Override
            public void onResponse(Call<List<PickupPointModel>> call, Response<List<PickupPointModel>> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                Log.d("RESPONSE",String.valueOf(response.code()));
                Log.e("NearbyPickupViewModel", "LiveData updated: " + response);

                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<PickupPointModel>> call, Throwable t) {
                Log.e("NearbyPickupViewModel", "LiveData updated: " + t.getMessage());
                userData.postValue(null);
            }
        });

    }
}
