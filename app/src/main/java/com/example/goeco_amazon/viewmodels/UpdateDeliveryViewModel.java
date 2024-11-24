package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.DeliveryModel;
import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.responsemodels.DeliveryResponse;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeliveryViewModel extends ViewModel {
    private MutableLiveData<UpdateDeliveryResponse> userData=new MutableLiveData<>();
    public LiveData<UpdateDeliveryResponse> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void updatedelivery(Application application, UpdateDelivery updateDelivery,String id){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.updatedelivery(id,updateDelivery).enqueue(new Callback<UpdateDeliveryResponse>() {
            @Override
            public void onResponse(Call<UpdateDeliveryResponse> call, Response<UpdateDeliveryResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<UpdateDeliveryResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
