package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.DeliveryModel;
import com.example.goeco_amazon.responsemodels.DeliveryResponse;
import com.example.goeco_amazon.responsemodels.GetDeliveryResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDeliveryViewModel extends ViewModel {
    private MutableLiveData<GetDeliveryResponse> userData=new MutableLiveData<>();
    public LiveData<GetDeliveryResponse> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void deliveries(Application application){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.getdeliveries(loginManager.getid()).enqueue(new Callback<GetDeliveryResponse>() {
            @Override
            public void onResponse(Call<GetDeliveryResponse> call, Response<GetDeliveryResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<GetDeliveryResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
