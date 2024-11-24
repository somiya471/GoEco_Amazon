package com.example.goeco_amazon.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goeco_amazon.MainActivity;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.adapters.NearbyPickupAdapter;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.util.ArrayList;

public class PickupMetricsActivity extends AppCompatActivity {

    ArrayList<PickupPointModel> pickupPoints;
    RecyclerView recyclerView,metricsrecycler;
    NearbyPickupAdapter adapter;
    TextView carbonsaved, caloriesburned, ecopoints;
    GetMetricsViewModel viewModel;
    ArrayList<MetricsData> metricsData;
    MetricsRecyclerAdapter metricsRecyclerAdapter;
    String mode = "";
    String id = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pickup_metrics);
        pickupPoints = getIntent().getParcelableArrayListExtra("pickupPointsList");


        recyclerView = findViewById(R.id.mode_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new NearbyPickupAdapter(this, pickupPoints, new PickuppointOnClick() {
//            @Override
//            public void onClick(PickupPointModel pointModel) {
//                id = pointModel.get_id();
//                viewModel = new ViewModelProvider(PickupMetricsActivity.this).get(GetMetricsViewModel.class);
//                viewModel.getUserLiveData().observe(PickupMetricsActivity.this, new Observer<MetricsResponse>() {
//                    @Override
//                    public void onChanged(MetricsResponse response) {
//                        if (response!=null) {
//                            metricsData = response.getData();
////                            Intent intent = new Intent(PickupMetricsActivity.this, PickupMetricsActivity.class);
////                            intent.putParcelableArrayListExtra("pickupPointsList", arrayList);
////                            startActivity(intent);
//                            metricsrecycler = findViewById(R.id.metrics_recycler_view);
//                            metricsrecycler.setLayoutManager(new LinearLayoutManager(PickupMetricsActivity.this));
//                            metricsRecyclerAdapter = new MetricsRecyclerAdapter(PickupMetricsActivity.this, metricsData, new MetricsCardOnClick() {
//                                @Override
//                                public void onclick(MetricsData metricsData) {
//                                    mode = metricsData.getMode();
//
//                                }
//                            });
//                            metricsrecycler.setAdapter(metricsRecyclerAdapter);
//
//                        }
//                    }
//                });

//            }
//        });
//        recyclerView.setAdapter(adapter);


// Use the pickupPoints list as needed, e.g., setting up a RecyclerView adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}