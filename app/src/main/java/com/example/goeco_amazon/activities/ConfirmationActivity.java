package com.example.goeco_amazon.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.adapters.NearbyPickupAdapter;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConfirmationActivity extends AppCompatActivity {
    ArrayList<PickupPointModel> pickupPoints;
    RecyclerView recyclerView,metricsrecycler;
    NearbyPickupAdapter adapter;
    GetMetricsViewModel viewModel;
    ArrayList<MetricsData> metricsData;
    MetricsRecyclerAdapter metricsRecyclerAdapter;
    String mode = "";
    String id = "";
    Button continuebtn;
    int selected = 0;
    LinearLayout deliverypage,modepage,orderpage;
    String delivery_option = "";
    MetricsModel metricsModel;
    LoginManager loginManager;
    CardView dooraddress;
    String address = "";
    int quantity;
    TextView productname,productprice,productdesc;
    ImageView imageView;
    TextView pickupname,pickupaddress,pickupdistance;
    String name,desc,image;
    int price;
    TextView deliverydate,timeslot,option,modetransport,deliveryaddress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);
        pickupPoints = getIntent().getParcelableArrayListExtra("pickupPointsList");
        quantity = getIntent().getIntExtra("quantity",0);
        price = getIntent().getIntExtra("productprice",0);
        name = getIntent().getStringExtra("productname");
        desc = getIntent().getStringExtra("productdesc");
        image = getIntent().getStringExtra("productimg");

        continuebtn = findViewById(R.id.continue_button);

        RadioGroup deliveryOptionsGroup = findViewById(R.id.delivery_options_group);

        loginManager = new LoginManager(this);
        // Set a listener for the RadioGroup
//        deliveryOptionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.delivery_option1:
//                        // Option 1 is selected
//                        Toast.makeText(MainActivity.this, "Selected: Nearby Pickup Points", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.delivery_option2:
//                        // Option 2 is selected
//                        Toast.makeText(MainActivity.this, "Selected: Door to Door Delivery", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });
        deliverypage = findViewById(R.id.delivery_options_layout);
        modepage = findViewById(R.id.mode_method_layout);
        orderpage = findViewById(R.id.order_summary_layout);
        dooraddress = findViewById(R.id.door_address);
        pickupname = findViewById(R.id.pickup_name);
        pickupaddress = findViewById(R.id.pickup_address);
        pickupdistance = findViewById(R.id.pickup_distance);
        recyclerView = findViewById(R.id.address_recycler_view);
        metricsrecycler = findViewById(R.id.metrics_recycler);

        deliverydate = findViewById(R.id.order_deliverydate);
        option = findViewById(R.id.order_option);
        timeslot = findViewById(R.id.order_timeslot);
        modetransport = findViewById(R.id.order_mode);
        deliveryaddress = findViewById(R.id.order_deliveryaddress);
        productname = findViewById(R.id.order_name);
        productdesc = findViewById(R.id.order_desc);
        productprice = findViewById(R.id.order_price);
        imageView = findViewById(R.id.order_image);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == 0){
                    selected = 1;
                    int selectedOptionId = deliveryOptionsGroup.getCheckedRadioButtonId();
                    if (selectedOptionId != -1) {
                        RadioButton selectedRadioButton = findViewById(selectedOptionId);
                        String selectedText = selectedRadioButton.getText().toString();
                        delivery_option = selectedText;
                        Toast.makeText(ConfirmationActivity.this, "Currently selected: " + selectedText, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConfirmationActivity.this, "No option selected", Toast.LENGTH_SHORT).show();
                    }
                    deliverypage.setVisibility(View.GONE);

                    if (delivery_option.equals("Nearby PickupPoints (RECOMMENDED)")){
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ConfirmationActivity.this));
                        adapter = new NearbyPickupAdapter(ConfirmationActivity.this, pickupPoints, new PickuppointOnClick() {
                            @Override
                            public void onClick(PickupPointModel pointModel) {
                                id = pointModel.get_id();
                                address = pointModel.getAddress();
                                metricsModel = new MetricsModel(pointModel.getDistance(),loginManager.getWeight());
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                    else if(delivery_option.equals("Door to door delivery")){
                        dooraddress.setVisibility(View.VISIBLE);
                        //LOGIC FOR THIS IS REMAINING
                    }
                }
                if (selected == 1){
                    selected = 2;
                    recyclerView.setVisibility(View.GONE);
                    dooraddress.setVisibility(View.GONE);
                    modepage.setVisibility(View.VISIBLE);
                    viewModel.metricsdata(ConfirmationActivity.this.getApplication(),metricsModel);
                    viewModel = new ViewModelProvider(ConfirmationActivity.this).get(GetMetricsViewModel.class);
                    viewModel.getUserLiveData().observe(ConfirmationActivity.this, new Observer<MetricsResponse>() {
                        @Override
                        public void onChanged(MetricsResponse response) {
                            if (response!=null) {
                                metricsData = response.getData();
//                            Intent intent = new Intent(PickupMetricsActivity.this, PickupMetricsActivity.class);
//                            intent.putParcelableArrayListExtra("pickupPointsList", arrayList);
//                            startActivity(intent);
                                metricsrecycler.setLayoutManager(new LinearLayoutManager(ConfirmationActivity.this));
                                metricsRecyclerAdapter = new MetricsRecyclerAdapter(ConfirmationActivity.this, metricsData, new MetricsCardOnClick() {
                                    @Override
                                    public void onclick(MetricsData metricsData) {
                                        mode = metricsData.getMode();

                                    }
                                });
                                metricsrecycler.setAdapter(metricsRecyclerAdapter);

                            }
                        }
                    });

                }
                if (selected == 2){
                    selected = 3;
                    continuebtn.setText("Place order");
                    modepage.setVisibility(View.GONE);
                    orderpage.setVisibility(View.VISIBLE);
                    Date currentDate = new Date();
                    System.out.println("Current Date and Time: " + currentDate);

                    // Format the current date to only show the date part
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateString = dateFormat.format(currentDate);

                    // Calculate the time 15 minutes from now
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.MINUTE, 15);
                    Date timeSlot = calendar.getTime();

                    // Format the time slot to show the desired time format
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String timeSlotString = timeFormat.format(timeSlot);
                    deliverydate.setText(currentDateString);
                    timeslot.setText(timeSlotString);
                    option.setText(delivery_option);
                    modetransport.setText(mode);
                    deliveryaddress.setText(address);
                    productname.setText(name);
                    productdesc.setText(desc);
                    productprice.setText("Rs. "+price);
                    Glide.with(ConfirmationActivity.this).load(image).into(imageView);


                }
                if (selected == 3){

                }


            }
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}