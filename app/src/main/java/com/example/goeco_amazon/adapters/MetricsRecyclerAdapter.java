package com.example.goeco_amazon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.MetricsData;

import java.util.ArrayList;
import java.util.List;

public class MetricsRecyclerAdapter extends RecyclerView.Adapter<MetricsRecyclerAdapter.MyViewHolder>{
    Context context;
    List<MetricsData> responses;
    MetricsCardOnClick metricsCardOnClick;


    public MetricsRecyclerAdapter(Context context,List<MetricsData> responses,MetricsCardOnClick metricsCardOnClick) {
        this.context = context;
        this.responses = responses;
        this.metricsCardOnClick = metricsCardOnClick;
    }
    @NonNull
    @Override
    public MetricsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_metrics,parent,false);
        return new MetricsRecyclerAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MetricsRecyclerAdapter.MyViewHolder holder, int position) {

        MetricsData metricsData = responses.get(position);
        holder.mode.setText(responses.get(position).getMode());
        holder.carbon.setText("Carbon saved: "+String.format("%.2f kg", responses.get(position).getCarbon_saved()));
        holder.calorie.setText("Calorie burned: "+String.format("%.2f cal", responses.get(position).getCalories_burned()));
        holder.ecopoints.setText("Ecopoints: "+responses.get(position).getEcoPoints()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metricsCardOnClick.onclick(metricsData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (responses != null && !responses.isEmpty()) ? responses.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mode,carbon,calorie,ecopoints;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mode = itemView.findViewById(R.id.mode_text);
            carbon = itemView.findViewById(R.id.carbon_saved_text);
            calorie = itemView.findViewById(R.id.calories_burned_text);
            ecopoints = itemView.findViewById(R.id.eco_points_text);


        }
    }
}
