package com.example.goeco_amazon.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;

import java.util.ArrayList;

public class NearbyPickupAdapter extends RecyclerView.Adapter<NearbyPickupAdapter.MyViewHolder>{
    Context context;
    ArrayList<PickupPointModel> responses;
    PickuppointOnClick pickuppointOnClick;


    public NearbyPickupAdapter(Context context,ArrayList<PickupPointModel> responses,PickuppointOnClick pickuppointOnClick) {
        this.context = context;
        this.responses = responses;
        this.pickuppointOnClick = pickuppointOnClick;
    }
    @NonNull
    @Override
    public NearbyPickupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pickup_item,parent,false);
        return new NearbyPickupAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NearbyPickupAdapter.MyViewHolder holder, int position) {

        PickupPointModel pointModel = responses.get(position);
        holder.name.setText(responses.get(position).getName());
        holder.address.setText(responses.get(position).getAddress());
        holder.distance.setText(String.format("%.2f km", responses.get(position).getDistance()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickuppointOnClick.onClick(pointModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return responses.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,address,distance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pickup_name);
            address = itemView.findViewById(R.id.pickup_address);
            distance = itemView.findViewById(R.id.pickup_distance);


        }
    }
}
