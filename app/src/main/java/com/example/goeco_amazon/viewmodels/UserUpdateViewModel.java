package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.models.UpdateUser;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.responsemodels.UpdateUserResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;
import com.example.goeco_amazon.utils.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateViewModel extends ViewModel {
    private MutableLiveData<UpdateUserResponse> userData=new MutableLiveData<>();
    public LiveData<UpdateUserResponse> getUserLiveData() { return userData; }
    LoginManager loginManager;

    public void updatedelivery(Application application, UpdateUser updateUser){
        loginManager = new LoginManager(application);
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.updateuser(updateUser).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}