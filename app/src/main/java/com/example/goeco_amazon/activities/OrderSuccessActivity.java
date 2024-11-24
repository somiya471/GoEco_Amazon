package com.example.goeco_amazon.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.goeco_amazon.MainActivity;
import com.example.goeco_amazon.R;

public class OrderSuccessActivity extends AppCompatActivity {
    private LottieAnimationView thumbsAnimation;
    private LottieAnimationView sparkleAnimation;
    private TextView successText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_success);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupAnimations();
        setupNavigation();
    }

    private void initializeViews() {
        thumbsAnimation = findViewById(R.id.thumbs_animation);
        sparkleAnimation = findViewById(R.id.sparkle_animation);
        successText = findViewById(R.id.success_text);
    }

    private void setupAnimations() {
        // Configure thumbs up animation
        thumbsAnimation.setAnimation("thumbs.json");
        thumbsAnimation.setSpeed(0.7f);
        thumbsAnimation.setRepeatCount(0);
        thumbsAnimation.playAnimation();

        // Configure sparkle animation
        sparkleAnimation.setAnimation("sparkle.json");
        sparkleAnimation.setSpeed(0.7f);
        sparkleAnimation.setRepeatCount(0);
        sparkleAnimation.playAnimation();
    }

    private void setupNavigation() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }, 1300);
    }
}