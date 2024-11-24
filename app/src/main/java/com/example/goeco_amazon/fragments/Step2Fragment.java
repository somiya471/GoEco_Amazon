package com.example.goeco_amazon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.adapters.NearbyPickupAdapter;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.util.ArrayList;
import java.util.List;


public class Step2Fragment extends Fragment {

    LoginManager loginManager;
    NearbyPickupViewModel nearbyPickupViewModel;
    List<PickupPointModel> pickupPoints;
    NearbyPickupAdapter adapter;
    RecyclerView recyclerView;
    String id = "";
    String address = "";

    public Step2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step2, container, false);
        loginManager = new LoginManager(getContext());
        view.findViewById(R.id.btnNext2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConfirmationActivity) requireActivity()).loadNextFragment();

            }
        });
//        if (loginManager.getOption().equals("Nearby PickupPoints (RECOMMENDED)")) {
            nearbyPickupViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(NearbyPickupViewModel.class);
            nearbyPickupViewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<List<PickupPointModel>>() {
                @Override
                public void onChanged(List<PickupPointModel> response) {
                    if (response != null) {
                        Log.d("HomeFragment", "Response received: " + response.toString());
//                    Toast.makeText(getContext(), "CLICKED", Toast.LENGTH_SHORT).show();
                        pickupPoints = response;
                        recyclerView = view.findViewById(R.id.address_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new NearbyPickupAdapter(getContext(), pickupPoints, new PickuppointOnClick() {
                            @Override
                            public void onClick(PickupPointModel pointModel) {
                                id = pointModel.get_id();
                                loginManager.setaid(id);
                                address = pointModel.getAddress();
                                loginManager.setAddress(address);
                                loginManager.setDistance(pointModel.getDistance());
                            }
                        });
                        recyclerView.setAdapter(adapter);

                    }else {
                        Log.e("Step2Fragment", "Received null response from LiveData.");
                    }
                }
            });
        Toast.makeText(getContext(), ""+loginManager.getLatitude() +"//"+loginManager.getLongitude(), Toast.LENGTH_SHORT).show();
            nearbyPickupViewModel.nearbypickup(requireActivity().getApplication(), loginManager.getLatitude(), loginManager.getLongitude());


//        }
//        else if(loginManager.getOption().equals("Door to door delivery")){
//            //LOGIC FOR THIS IS REMAINING
//        }


        return view;
    }
}