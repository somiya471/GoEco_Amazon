package com.example.goeco_amazon.utils;


import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static final String TAG = "App";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //   Fabric.with(this, new Crashlytics());

        context = getApplicationContext();

    }



    public static Context getContext() {
        return context;
    }
}