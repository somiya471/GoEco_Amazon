package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.RegisterResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserViewModel extends ViewModel {
    private MutableLiveData<RegisterResponse> userData=new MutableLiveData<>();
    public LiveData<RegisterResponse> getUserLiveData() { return userData; }

    public void registeruser(Application application, UserModel userModel){
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.postregister(userModel).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
