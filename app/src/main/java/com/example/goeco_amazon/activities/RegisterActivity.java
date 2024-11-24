package com.example.goeco_amazon.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
    private double latitude = 70.6754, longitude = 50.6785;
    RegisterUserViewModel registerUserViewModel;
    LoginManager loginManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

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

        // Check if location services are enabled
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        checkLocationPermissions();

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
            // Check permissions again
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // Try to get last known location first
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
                loginManager.setLatitude(latitude);
                loginManager.setLongitude(longitude);
            }

            // Request location updates from both providers
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    loginManager.setLatitude(latitude);
                    loginManager.setLongitude(longitude);
                    Toast.makeText(RegisterActivity.this, "Location updated: " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(RegisterActivity.this, provider + " is disabled. Please enable location services", Toast.LENGTH_SHORT).show();
                }
            };

            // Request updates from GPS provider
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,  // 5 seconds
                    5,     // 5 meters
                    locationListener
            );

            // Also request updates from Network provider as backup
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,  // 5 seconds
                    5,     // 5 meters
                    locationListener
            );

        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error accessing location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Using default location.", Toast.LENGTH_SHORT).show();
            }
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
                    String id = userResponse.getUser().get_id();
                    loginManager.setid(id);
                    Toast.makeText(RegisterActivity.this, "Register successful"+userResponse.getUser().get_id(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}
