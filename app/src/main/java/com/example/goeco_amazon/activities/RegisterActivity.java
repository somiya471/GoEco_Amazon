package com.example.goeco_amazon.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.RegisterResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.RegisterUserViewModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etWeight;
    private Button btnRegister;
    private LocationManager locationManager;
    private double latitude, longitude;
    RegisterUserViewModel registerUserViewModel;
    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etWeight = findViewById(R.id.etWeight);
        btnRegister = findViewById(R.id.btnRegister);
        loginManager = new LoginManager(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Request Location Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLocation();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String weight = etWeight.getText().toString();
                if(username.isEmpty() || password.isEmpty() || weight.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }else{
                    int weight2 = Integer.parseInt(weight);
                    loginManager.setWeight(weight2);
                    btnuserregister(username,password,weight2,latitude,longitude);
                }


            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void getLocation() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(RegisterActivity.this, "Enable GPS for location", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private void btnuserregister(String username,String password,int weight,double latitude,double longitude) {

        UserModel user = new UserModel(username,password,weight,latitude,longitude);
        initViewModel();
        registerUserViewModel.registeruser(this.getApplication(),user);
    }

    private void initViewModel(){
        registerUserViewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);
        registerUserViewModel.getUserLiveData().observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(RegisterActivity.this, "Register Failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}
