package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMetricsViewModel extends ViewModel {
    private MutableLiveData<List<MetricsData>> userData=new MutableLiveData<>();
    public LiveData<List<MetricsData>> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void metricsdata(Application application, double distance,int weight){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getmetrics(distance,weight).enqueue(new Callback<List<MetricsData>>() {
            @Override
            public void onResponse(Call<List<MetricsData>> call, Response<List<MetricsData>> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<MetricsData>> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}