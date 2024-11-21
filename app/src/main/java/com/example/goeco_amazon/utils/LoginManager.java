package com.example.goeco_amazon.utils;

import android.content.Context;
import android.content.SharedPreferences;



public class LoginManager {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    //  on sign up complete
    //  login_manager1.SetLoginStatus(false);
    //  if(login_manager.isLoggedIn()) logged in
    public static LoginManager Instance;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String Preference_name = "login_manager_1";

    private static final String IS_LOGED_IN = "islogin";
    private static final String messageToken="firebase_messaging_token";
    private static final String isMessageTokenChanged="firebase_messaging_token";

    private static String name = "name";

    private static String strweight = "weight";

    private static String mobilenumber = "phone";

    private static String username = "username";

    private static String password = "password";

    private static String token = "token";

    public static LoginManager getInstance() {

        if (Instance == null) {
            Instance = new LoginManager(App.getContext());
        }

        return Instance;
    }


    public LoginManager(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(Preference_name, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void removeSharedPreference() {
        editor.clear();
        editor.apply();
    }

    public void SetLoginStatus(boolean isFirstTime) {
        editor.putBoolean(IS_LOGED_IN, isFirstTime);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGED_IN, false);
    }


    public void setname(String mname) {
        editor.putString(name, mname).commit();
    }

    public String getname() {
        return sharedPreferences.getString(name, "");
    }

    public void setWeight(int weight) {
        editor.putInt(strweight,weight).commit();
    }

    public int getWeight() {
        return sharedPreferences.getInt(strweight,0);

    }

    public void setNumber(String number) {
        editor.putString(mobilenumber, number).commit();
    }

    public String getnumber() {
        return sharedPreferences.getString(mobilenumber, "");

    }

    public void setPassword(String pass) {
        editor.putString(password, pass).commit();
    }

    public String getPassword() {
        return sharedPreferences.getString(password, "");

    }

    public void settoken(String tok) {
        editor.putString(token, tok).apply();
    }

    public String gettoken() {
        return sharedPreferences.getString(token, "");
    }

    public void setFirebase_token(String token) {
        editor.putString(messageToken,token);
    }

    public void setIs_firebase_token_changed(boolean b) {
        editor.putBoolean(isMessageTokenChanged,b);
    }
    public String getMessageToken(){
        return sharedPreferences.getString(messageToken, "");
    }
}