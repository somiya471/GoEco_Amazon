package com.example.goeco_amazon.fragments;

import android.app.Application;
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
import android.widget.TextView;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.adapters.LeaderboardRecyclerAdapter;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.responsemodels.GetLeaderboardResponse;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.ProfileResponse;
import com.example.goeco_amazon.responsemodels.UserLeaderboard;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;
import com.example.goeco_amazon.viewmodels.LeaderboardViewModel;
import com.example.goeco_amazon.viewmodels.ProfileViewModel;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    TextView profilename,ecopoints;
    ProfileViewModel viewModel;
    RecyclerView recyclerView;
    LeaderboardViewModel leaderboardViewModel;
    LeaderboardRecyclerAdapter adapter;
    ArrayList<UserLeaderboard> arrayList;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilename = view.findViewById(R.id.profile_name);
        ecopoints = view.findViewById(R.id.profile_ecopoints);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(ProfileViewModel.class);
        viewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<ProfileResponse>() {
            @Override
            public void onChanged(ProfileResponse response) {
                if (response!=null) {
                    profilename.setText(response.getData().get(0).getUsername());
                    ecopoints.setText(response.getData().get(0).getEcoPoints() + "");
                }
            }
        });
        viewModel.metricsdata(requireActivity().getApplication());


        leaderboardViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(LeaderboardViewModel.class);
        leaderboardViewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<GetLeaderboardResponse>() {
            @Override
            public void onChanged(GetLeaderboardResponse response) {
                if (response!=null) {
                    arrayList = response.getLeaderboard();
                    recyclerView = view.findViewById(R.id.leaderboard_recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new LeaderboardRecyclerAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        leaderboardViewModel.metricsdata(requireActivity().getApplication());

        return view;
    }
}