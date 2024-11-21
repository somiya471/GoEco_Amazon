package com.example.goeco_amazon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.goeco_amazon.activities.PickupMetricsActivity;
import com.example.goeco_amazon.adapters.ImageSliderAdapter;
import com.example.goeco_amazon.fragments.DeliveryFragment;
import com.example.goeco_amazon.fragments.HomeFragment;
import com.example.goeco_amazon.fragments.ProfileFragment;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    NearbyPickupViewModel viewModel;
    ArrayList<PickupPointModel> arrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_home){
                    selectedFragment = new HomeFragment();
                }
                if (id == R.id.nav_delivery){
                    selectedFragment = new DeliveryFragment();
                }
                if (id == R.id.nav_profile){
                    selectedFragment = new ProfileFragment();
                }
                return loadFragment(selectedFragment);
            }
        });

        // Load default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}