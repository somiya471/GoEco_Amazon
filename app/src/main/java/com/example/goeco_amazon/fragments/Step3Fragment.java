package com.example.goeco_amazon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;

import java.util.ArrayList;
import java.util.List;


public class Step3Fragment extends Fragment {

    GetMetricsViewModel viewModel;
    List<MetricsData> metricsData;
    MetricsRecyclerAdapter metricsRecyclerAdapter;
    RecyclerView metricsrecycler;
    String mode = "";
    LoginManager loginManager;

    public Step3Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step3, container, false);
        loginManager = new LoginManager(getContext());
        view.findViewById(R.id.btnNext3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConfirmationActivity) requireActivity()).loadNextFragment();

            }
        });
        viewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(GetMetricsViewModel.class);
        viewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<List<MetricsData>>() {
            @Override
            public void onChanged(List<MetricsData> response) {
                if (response!=null) {
                    metricsData = response;
                    metricsrecycler = view.findViewById(R.id.metrics_recycler);
                    metricsrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    metricsRecyclerAdapter = new MetricsRecyclerAdapter(getContext(), metricsData, new MetricsCardOnClick() {
                        @Override
                        public void onclick(MetricsData metricsData) {
                            mode = metricsData.getMode();
                            loginManager.setmode(mode);

                        }
                    });
                    metricsrecycler.setAdapter(metricsRecyclerAdapter);

                }
            }
        });
        viewModel.metricsdata(requireActivity().getApplication(),loginManager.getDistance(),loginManager.getWeight());

        return view;
    }
}