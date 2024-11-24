package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.SoloMetricsData;
import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.responsemodels.SoloMetricsResponse;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoloMetricsViewModel extends ViewModel {
    private MutableLiveData<SoloMetricsResponse> userData=new MutableLiveData<>();
    public LiveData<SoloMetricsResponse> getUserLiveData() { return userData; }

    public void solometrics(Application application, SoloMetricsData data){
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getsolometrics(data.getLat(),data.getLon(),data.getLat1(),data.getLon1(),data.getUserWeight(),data.getMode_of_transport()).enqueue(new Callback<SoloMetricsResponse>() {
            @Override
            public void onResponse(Call<SoloMetricsResponse> call, Response<SoloMetricsResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<SoloMetricsResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
