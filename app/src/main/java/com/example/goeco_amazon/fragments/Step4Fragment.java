package com.example.goeco_amazon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.utils.LoginManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Step4Fragment extends Fragment {
    String currentDateString = "";
    String timeSlotString = "";
    TextView productname,productprice,productdesc;
    TextView deliverydate,timeslot,option,modetransport,deliveryaddress;
    ImageView imageView;


    LoginManager loginManager;
    public Step4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step4, container, false);
        loginManager = new LoginManager(getContext());
        Date currentDate = new Date();
        System.out.println("Current Date and Time: " + currentDate);

        // Format the current date to only show the date part
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDateString = dateFormat.format(currentDate);

        // Calculate the time 15 minutes from now
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, 15);
        Date timeSlot = calendar.getTime();
        deliverydate = view.findViewById(R.id.order_deliverydate);
        timeslot = view.findViewById(R.id.order_timeslot);
        option = view.findViewById(R.id.order_option);
        modetransport = view.findViewById(R.id.order_mode);
        deliveryaddress = view.findViewById(R.id.order_deliveryaddress);
        productname = view.findViewById(R.id.order_name);
        productdesc = view.findViewById(R.id.order_desc);
        productprice = view.findViewById(R.id.order_price);
        imageView = view.findViewById(R.id.order_image);

        // Format the time slot to show the desired time format
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeSlotString = timeFormat.format(timeSlot);
        loginManager.setCurrentdate(currentDateString);
        loginManager.setTimeslot(timeSlotString);

        deliverydate.setText(currentDateString);
        timeslot.setText(timeSlotString);
        option.setText(loginManager.getOption());
        modetransport.setText(loginManager.getmode());
        deliveryaddress.setText(loginManager.getAddress());
        productname.setText(loginManager.getName());
        productdesc.setText(loginManager.getDesc());
        productprice.setText("Rs. "+loginManager.getPrice());
        Glide.with(getContext()).load(loginManager.getImage()).into(imageView);
        return view;
    }
}