package com.example.goeco_amazon.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.MovementActivity;
import com.example.goeco_amazon.adapters.GetDeliveryRecyclerAdapter;
import com.example.goeco_amazon.interfaces.CancelbtnOnClick;
import com.example.goeco_amazon.interfaces.StartbtnOnClick;
import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.responsemodels.GetDeliveryData;
import com.example.goeco_amazon.responsemodels.GetDeliveryResponse;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.viewmodels.GetDeliveryViewModel;
import com.example.goeco_amazon.viewmodels.UpdateDeliveryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DeliveryFragment extends Fragment {
    RecyclerView recyclerView;
    GetDeliveryRecyclerAdapter adapter;
    ArrayList<GetDeliveryData> data;
    GetDeliveryViewModel viewModel;
    UpdateDeliveryViewModel updateDeliveryViewModel;


    public DeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(GetDeliveryViewModel.class);
        viewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<GetDeliveryResponse>() {
            @Override
            public void onChanged(GetDeliveryResponse response) {
                if (response!=null) {
                    data = response.getData();
//                            Intent intent = new Intent(PickupMetricsActivity.this, PickupMetricsActivity.class);
//                            intent.putParcelableArrayListExtra("pickupPointsList", arrayList);
//                            startActivity(intent);
                    recyclerView = view.findViewById(R.id.recycler_delivery);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new GetDeliveryRecyclerAdapter(getContext(), data, new CancelbtnOnClick() {
                        @Override
                        public void onclick(GetDeliveryData data) {
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            updatedelivery("canceled",timeFormat.format(new Date()),0,data.get_id(),data.getMode_of_transport());
                        }
                    }, new StartbtnOnClick() {
                        @Override
                        public void onclick(GetDeliveryData data) {
                            Intent i = new Intent(getContext(), MovementActivity.class);
                            i.putExtra("latitude",data.getDelivery_address().getLatitude());
                            i.putExtra("longtitude",data.getDelivery_address().getLatitude());
                            i.putExtra("name",data.getDelivery_address().getName());
                            i.putExtra("id",data.get_id());
                            i.putExtra("address",data.getDelivery_address().getAddress());
                            startActivity(i);
                        }
                    });
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        viewModel.deliveries(requireActivity().getApplication());



        return view;
    }
    private void updatedelivery(String status,String receivetime,int ecoPoints,String id,String mode) {

        UpdateDelivery updateDelivery = new UpdateDelivery(status,receivetime,ecoPoints,0.0,0.0,mode);
        initViewModel();
        updateDeliveryViewModel.updatedelivery(getActivity().getApplication(),updateDelivery,id);
    }

    private void initViewModel(){
        updateDeliveryViewModel = new ViewModelProvider(this).get(UpdateDeliveryViewModel.class);
        updateDeliveryViewModel.getUserLiveData().observe(this, new Observer<UpdateDeliveryResponse>() {
            @Override
            public void onChanged(UpdateDeliveryResponse updateDeliveryResponse) {
                if (updateDeliveryResponse == null) {
                    Toast.makeText(getContext(), "Register Failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}