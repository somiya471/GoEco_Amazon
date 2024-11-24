package com.example.goeco_amazon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.UserLeaderboard;

import java.util.ArrayList;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.MyViewHolder>{
    Context context;
    ArrayList<UserLeaderboard> responses;


    public LeaderboardRecyclerAdapter(Context context,ArrayList<UserLeaderboard> responses) {
        this.context = context;
        this.responses = responses;
    }
    @NonNull
    @Override
    public LeaderboardRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false);
        return new LeaderboardRecyclerAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerAdapter.MyViewHolder holder, int position) {

        holder.name.setText(responses.get(position).getUsername());
        holder.position.setText(responses.get(position).getPosition()+"");
        holder.ecopoints.setText("Ecopoints: "+responses.get(position).getEcoPoints()+"");
//        holder.distance.setText(String.format("%.2f km", responses.get(position).getDistance()));


    }

    @Override
    public int getItemCount() {
        return (responses != null && !responses.isEmpty()) ? responses.size() : 0;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView position,name,ecopoints;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
            name = itemView.findViewById(R.id.leaderboard_name);
            ecopoints = itemView.findViewById(R.id.leaderboard_ecopoints);


        }
    }
}
