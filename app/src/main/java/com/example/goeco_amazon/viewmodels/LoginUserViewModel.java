package com.example.goeco_amazon.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.RegisterResponse;
import com.example.goeco_amazon.restService.ApiInterface;
import com.example.goeco_amazon.restService.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> userData=new MutableLiveData<>();
    public LiveData<LoginResponse> getUserLiveData() { return userData; }

    public void loginuser(Application application, UserLogin userLogin){
        ApiInterface retro= RetrofitBuilder.getInstance(application).getApi();
        retro.postlogin(userLogin).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("RESPONSE",String.valueOf(response.body()));
                if(response.code()==200 || response.code() == 201){
                    userData.postValue(response.body());
                }else{
                    userData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                userData.postValue(null);
            }
        });

    }
}
