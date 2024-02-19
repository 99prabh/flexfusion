package com.app.flexfusion.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.databinding.ActivityLoginBinding;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    String email;

    String password;

    FirebaseAuth auth;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Wait...");
        progressDialog.setCancelable(false);



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
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                if (user.getUid().equals(Utils.ADMIN_UID)) {
                    Utils.isAdmin = true;
                }
                new DatabaseHelper().getCurrentUserData().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.hasChild("bodyNeedWater") || Utils.isAdmin) {
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
                    progressDialog.dismiss();
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
            }

        });
    }
}