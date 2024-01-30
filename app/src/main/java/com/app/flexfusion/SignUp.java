package com.app.flexfusion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    String name,email;
    String password;
    String confirmPassword;

    FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Wait...");
        progressDialog.setCancelable(false);

        binding.btnSignup.setOnClickListener(v -> {
            name=binding.etName.getText().toString();
            email=binding.etEmail.getText().toString();
            password=binding.etPassword.getText().toString();
            confirmPassword=binding.etConfirmpassword.getText().toString();
            if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(SignUp.this,"Please fill all the fields.",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!confirmPassword.equals(password)){
                Toast.makeText(SignUp.this,"Password not matched.",Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            signupUser(email,password);
        });

        binding.tvSignIn.setOnClickListener(v -> {
            Intent intent=new Intent(SignUp.this,Login.class);
            startActivity(intent);
        });

    }

    private void signupUser(String email,String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this,task -> {
            if (task.isSuccessful()){
                progressDialog.hide();
                FirebaseUser user=auth.getCurrentUser();
                // Save user's name to Realtime Database
                saveUserNameToDatabase(user.getUid(), name);
                Toast.makeText(SignUp.this,"User Registered Successfully!",Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
            else {
                progressDialog.hide();
                Toast.makeText(SignUp.this,"Registration Failed!",Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void saveUserNameToDatabase(String userId, String name) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");

        // Create a new user object with the provided name
        Users users = new Users(userId, name);

        // Save the user object to the "users" table in the Realtime Database
        db.child(userId).setValue(users);
    }
}