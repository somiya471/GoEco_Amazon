package com.example.goeco_amazon.activities;

import static com.example.goeco_amazon.utils.App.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goeco_amazon.MainActivity;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.adapters.NearbyPickupAdapter;
import com.example.goeco_amazon.fragments.Step1Fragment;
import com.example.goeco_amazon.fragments.Step2Fragment;
import com.example.goeco_amazon.fragments.Step3Fragment;
import com.example.goeco_amazon.fragments.Step4Fragment;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.models.DeliveryModel;
import com.example.goeco_amazon.models.MetricsModel;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.responsemodels.DeliveryResponse;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.MetricsResponse;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.AddDeliveryViewModel;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;
import com.example.goeco_amazon.viewmodels.LoginUserViewModel;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ConfirmationActivity extends AppCompatActivity {
    Button continuebtn;
    LoginManager loginManager;
    int quantity;
    String name,desc,image,pid;
    int price;
    TextView step2,step3,step4;
    AddDeliveryViewModel addDeliveryViewModel;

    private int currentStep = 1; // To track the current step



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);
//        pickupPoints = getIntent().getParcelableArrayListExtra("pickupPointsList");
        quantity = getIntent().getIntExtra("quantity",0);
        price = getIntent().getIntExtra("productprice",0);
        name = getIntent().getStringExtra("productname");
        desc = getIntent().getStringExtra("productdesc");
        image = getIntent().getStringExtra("productimg");
        pid = getIntent().getStringExtra("productid");

        continuebtn = findViewById(R.id.continue_button);
        loginManager = new LoginManager(this);
        loginManager.setQuantity(
                quantity
        );
        loginManager.setpid(pid);
        loginManager.setPrice(price);
        loginManager.setName(name);
        loginManager.setDesc(desc);
        loginManager.setImage(image);
        step2 = findViewById(R.id.step_two_indicator);
        step3 = findViewById(R.id.step_three_indicator);
        step4 = findViewById(R.id.step_four_indicator);


        loadFragment(new Step1Fragment());

        Button btnContinue = findViewById(R.id.continue_button);
        btnContinue.setOnClickListener(view -> {
            // Handle "Continue" button click
            btnadddelivery(pid,quantity,loginManager.getCurrentdate(),loginManager.getTimeslot(),loginManager.getmode(),loginManager.getaid(),loginManager.getid());
            Toast.makeText(this, "Process Complete"+loginManager.getid(), Toast.LENGTH_SHORT).show();
        });

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







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loadNextFragment() {
        Fragment fragment = null;
        int activeColor = Color.parseColor("#008397"); // Your blue color

        if (currentStep == 1) {
            fragment = new Step2Fragment();
            currentStep++;
            step2.setBackgroundTintList(ColorStateList.valueOf(activeColor));
        } else if (currentStep == 2) {
            fragment = new Step3Fragment();
            currentStep++;
            step3.setBackgroundTintList(ColorStateList.valueOf(activeColor));
        } else if (currentStep == 3) {
            fragment = new Step4Fragment();
            step4.setBackgroundTintList(ColorStateList.valueOf(activeColor));
            findViewById(R.id.continue_button).setVisibility(View.VISIBLE);
        }

        if (fragment != null) {
            loadFragment(fragment);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    private void btnadddelivery(String product,int quantity,String delivery_date,String timeslot,String mode,String address,String user) {

        DeliveryModel deliveryModel = new DeliveryModel(product,quantity,delivery_date,timeslot,mode,address,user);
        initViewModel();
        addDeliveryViewModel.deliveryadd(this.getApplication(),deliveryModel);
    }

    private void initViewModel(){
        addDeliveryViewModel = new ViewModelProvider(this).get(AddDeliveryViewModel.class);
        addDeliveryViewModel.getUserLiveData().observe(this, new Observer<DeliveryResponse>() {
            @Override
            public void onChanged(DeliveryResponse deliveryResponse) {
                if (deliveryResponse == null) {
                    Toast.makeText(ConfirmationActivity.this, "Delivery add Failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfirmationActivity.this, "Delivery add Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ConfirmationActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}