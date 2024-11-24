package com.example.goeco_amazon.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.interfaces.CancelbtnOnClick;
import com.example.goeco_amazon.interfaces.StartbtnOnClick;
import com.example.goeco_amazon.responsemodels.GetDeliveryData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GetDeliveryRecyclerAdapter extends RecyclerView.Adapter<GetDeliveryRecyclerAdapter.MyViewHolder>{
    private final Context context;
    private final ArrayList<GetDeliveryData> data;
    CancelbtnOnClick cancelbtnOnClick;
    StartbtnOnClick startbtnOnClick;

    public GetDeliveryRecyclerAdapter(Context context, ArrayList<GetDeliveryData> data,CancelbtnOnClick cancelbtnOnClick,StartbtnOnClick startbtnOnClick) {
        this.context = context;
        this.data = data;
        this.cancelbtnOnClick = cancelbtnOnClick;
        this.startbtnOnClick = startbtnOnClick;
    }

    @NonNull
    @Override
    public GetDeliveryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_item, parent, false);
        return new GetDeliveryRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetDeliveryRecyclerAdapter.MyViewHolder holder, int position) {
        GetDeliveryData delivery = data.get(position);

        // Ensure no null data
        if (delivery != null) {
            if (delivery.getProduct() != null) {
                holder.prodname.setText(delivery.getProduct().getName() != null ? delivery.getProduct().getName() : "N/A");
                holder.prodprice.setText(delivery.getProduct().getPrice() != 0 ? "Price: "+ delivery.getProduct().getPrice()+"" : "0.00");
            }
            holder.prodquantity.setText(delivery.getQuantity() != 0 ? "Quantity: "+delivery.getQuantity()+"" : "0");

            holder.date.setText(delivery.getDelivery_date() != null ? "Delivery date: "+delivery.getDelivery_date() : "N/A");
            holder.timeslot.setText(delivery.getTime_slot() != null ? "Delivery timeslot: "+ delivery.getTime_slot() : "N/A");
            holder.deliverystatus.setText(delivery.getDelivery_status() != null ? "Delivery status: "+delivery.getDelivery_status() : "Pending");

            // Handle address nullability
            if (delivery.getDelivery_address() != null) {
                String address = delivery.getDelivery_address().getName() + ": " + delivery.getDelivery_address().getAddress();
                holder.address.setText(address != null ? address : "N/A");
            }

            if ("delivered".equals(delivery.getDelivery_status())) {
                holder.btnlayout.setVisibility(View.GONE);
            }

            boolean isToday = isTodayAndAfterTime(delivery.getDelivery_date(), delivery.getTime_slot());
            holder.start.setEnabled(isToday);

            holder.cancel.setOnClickListener(v -> cancelbtnOnClick.onclick(delivery));
            holder.start.setOnClickListener(v -> startbtnOnClick.onclick(delivery));
        }
    }


    @Override
    public int getItemCount() {
        return (data != null && !data.isEmpty()) ? data.size() : 0;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prodname,prodprice,prodquantity,date,timeslot,deliverystatus,address;
        Button cancel,start;
        LinearLayout btnlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prodname = itemView.findViewById(R.id.delivery_prod_name);
            prodprice = itemView.findViewById(R.id.delivery_prod_price);
            prodquantity = itemView.findViewById(R.id.delivery_prod_quantity);
            date = itemView.findViewById(R.id.delivery_prod_date);
            timeslot = itemView.findViewById(R.id.delivery_prod_timeslot);
            deliverystatus = itemView.findViewById(R.id.delivery_prod_status);
            address = itemView.findViewById(R.id.delivery_prod_address);
            cancel = itemView.findViewById(R.id.btn_cancel);
            start = itemView.findViewById(R.id.btn_start);
            btnlayout = itemView.findViewById(R.id.btnlayout);

        }
    }

    private boolean isTodayAndAfterTime(String deliveryDate, String timeSlot) {
        try {
            // Parse delivery date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                LocalDate deliveryLocalDate = LocalDate.parse(deliveryDate, DateTimeFormatter.ISO_LOCAL_DATE);

            // Parse time slot
            LocalTime deliveryTime = null;
            deliveryTime = LocalTime.parse(timeSlot, DateTimeFormatter.ofPattern("HH:mm"));


            // Get current date and time
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            // Check if date is today and time is after
            return currentDate.isEqual(deliveryLocalDate) && currentTime.isAfter(deliveryTime);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Default to false in case of parsing errors
        }
    }
}
