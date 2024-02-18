package com.app.flexfusion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.app.flexfusion.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    String email;

    String password;

    FirebaseAuth auth;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Wait...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        binding.btnSignin.setOnClickListener(v -> {
            email = binding.etEmail.getText().toString();
            password = binding.etPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            loginUser(email, password);
        });

        binding.tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

    }

    private void loginUser(String email, String password) {
        int setupCompleted = sharedPreferences.getInt("setup_completed", 0);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, task -> {
            if (task.isSuccessful()) {
                progressDialog.show();
                FirebaseUser user = auth.getCurrentUser();
                if (setupCompleted == 1) {
                    // Setup is completed, move to home screen
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Setup is not completed, move to setup screens
                    Intent intent = new Intent(Login.this, Splash2.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
}