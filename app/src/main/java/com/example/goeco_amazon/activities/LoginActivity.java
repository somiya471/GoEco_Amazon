package com.example.goeco_amazon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.goeco_amazon.MainActivity;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.models.UserModel;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.RegisterResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.LoginUserViewModel;
import com.example.goeco_amazon.viewmodels.RegisterUserViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    LoginUserViewModel loginUserViewModel;
    LoginManager loginManager;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginManager = new LoginManager(this);
        signup = findViewById(R.id.textViewSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    btnuserlogin(username,password);
                    // Authenticate user (Use SharedPreferences or DB for actual authentication)
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void btnuserlogin(String username,String password) {

        UserLogin user = new UserLogin(username,password);
        initViewModel();
        loginUserViewModel.loginuser(this.getApplication(),user);
    }

    private void initViewModel(){
        loginUserViewModel = new ViewModelProvider(this).get(LoginUserViewModel.class);
        loginUserViewModel.getUserLiveData().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(LoginActivity.this, "Register Failure", Toast.LENGTH_SHORT).show();
                } else {
//                    loginManager.settoken(userResponse.getToken());
                    Toast.makeText(LoginActivity.this, "Login Successful"+loginManager.getid(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}