package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.ProfileResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileResponse> userData=new MutableLiveData<>();
    public LiveData<ProfileResponse> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void metricsdata(Application application){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getprofile(loginManager.getid()).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
