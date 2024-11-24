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

//    private static final String IS_LOGED_IN = "islogin";
//    private static final String messageToken="firebase_messaging_token";
//    private static final String isMessageTokenChanged="firebase_messaging_token";

    private static String id = "id";

    private static String strweight = "weight";

    private static String latitude = "latitude";

    private static String longitude = "longitude";

    private static String distance = "distance";
    private static String mode = "mode";
    private static String pid = "pid";
    private static String option = "option";
    private static String quantity = "quantity";
    private static String currentdate = "currentdate";
    private static String timeslot = "timeslot";
    private static String aid = "aid";

    private static String address = "address";
    private static String name = "name";
    private static String desc = "desc";
    private static String price = "price";
    private static String image = "image";

//    private static String token = "token";

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

//    public void SetLoginStatus(boolean isFirstTime) {
//        editor.putBoolean(IS_LOGED_IN, isFirstTime);
//        editor.commit();
//    }
//
//    public boolean isLoggedIn() {
//        return sharedPreferences.getBoolean(IS_LOGED_IN, false);
//    }


    public void setid(String id) {
        editor.putString(this.id, id).commit();
    }
    public String getid() {
        return sharedPreferences.getString(this.id, "");
    }

    public void setAddress(String ad) {
        editor.putString(address, ad).commit();
    }
    public String getAddress() {
        return sharedPreferences.getString(address, "");
    }

    public void setName(String id) {
        editor.putString(name, id).commit();
    }
    public String getName() {
        return sharedPreferences.getString(name, "");
    }

    public void setDesc(String id) {
        editor.putString(desc, id).commit();
    }
    public String getDesc() {
        return sharedPreferences.getString(desc, "");
    }

    public void setImage(String id) {
        editor.putString(image, id).commit();
    }
    public String getImage() {
        return sharedPreferences.getString(image, "");
    }

    public void setpid(String id) {
        editor.putString(pid, id).commit();
    }
//    public String getpid() {
//        return sharedPreferences.getString(pid, "");
//    }

    public void setOption(String id) {
        editor.putString(option, id).commit();
    }
    public String getOption() {
        return sharedPreferences.getString(option, "");
    }

    public void setaid(String id) {
        editor.putString(aid, id).commit();
    }
    public String getaid() {
        return sharedPreferences.getString(aid, "");
    }

    public void setmode(String id) {
        editor.putString(mode, id).commit();
    }
    public String getmode() {
        return sharedPreferences.getString(mode, "");
    }

    public void setCurrentdate(String id) {
        editor.putString(currentdate, id).commit();
    }

    public String getCurrentdate() {
        return sharedPreferences.getString(currentdate, "");
    }

    public void setTimeslot(String id) {
        editor.putString(timeslot, id).commit();
    }

    public String getTimeslot() {
        return sharedPreferences.getString(timeslot, "");
    }

    public void setWeight(int weight) {
        editor.putInt(strweight,weight).commit();
    }

    public int getWeight() {
        return sharedPreferences.getInt(strweight,0);

    }
    public void setQuantity(int quantity) {
        editor.putInt(this.quantity,quantity).commit();
    }

    public int getQuantity() {
        return sharedPreferences.getInt(quantity,0);

    }

    public void setPrice(int weight) {
        editor.putInt(price,weight).commit();
    }

    public int getPrice() {
        return sharedPreferences.getInt(price,0);

    }

    public void setLatitude(double latitude) {
        editor.putString(this.latitude, String.valueOf(latitude)).commit(); // Convert double to String
    }

    public double getLatitude() {
        String latitude = sharedPreferences.getString(this.latitude, "0.0"); // Default to "0.0"
        return Double.parseDouble(latitude); // Convert String back to double
    }

    public void setLongitude(double longitude) {
        editor.putString(this.longitude, String.valueOf(longitude)).commit(); // Convert double to String
    }

    public double getLongitude() {
        String value = sharedPreferences.getString(longitude, "0.0"); // Default to "0.0"
        return Double.parseDouble(value); // Convert String back to double
    }
    public void setDistance(double pass) {
        editor.putString(distance, String.valueOf(pass)).commit(); // Convert double to String
    }

    public double getDistance() {
        String value = sharedPreferences.getString(distance, "0.0"); // Default to "0.0"
        return Double.parseDouble(value); // Convert String back to double
    }


//    public void settoken(String tok) {
//        editor.putString(token, tok).apply();
//    }
//
//    public String gettoken() {
//        return sharedPreferences.getString(token, "");
//    }
//
//    public void setFirebase_token(String token) {
//        editor.putString(messageToken,token);
//    }
//
//    public void setIs_firebase_token_changed(boolean b) {
//        editor.putBoolean(isMessageTokenChanged,b);
//    }
//    public String getMessageToken(){
//        return sharedPreferences.getString(messageToken, "");
//    }
}