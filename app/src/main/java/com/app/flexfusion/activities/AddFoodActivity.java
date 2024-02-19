package com.app.flexfusion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.flexfusion.R;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class AddFoodActivity extends AppCompatActivity {

    ImageView ivBack;
    EditText edtName;
    EditText edtServingName;
    EditText edtSize;
    EditText edtCalories;

    Button btnAddFood;

    DatabaseHelper databaseHelper;

    FirebaseAuth auth;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        ivBack = findViewById(R.id.ivBack);
        edtName = findViewById(R.id.edtFoodName);
        edtServingName = findViewById(R.id.edtServingName);
        edtSize = findViewById(R.id.edtSize);
        edtCalories = findViewById(R.id.edtCalories);
        btnAddFood = findViewById(R.id.btnAddFood);

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();


        ivBack.setOnClickListener(v -> finish());
        databaseHelper = new DatabaseHelper();

        String category = getIntent().getStringExtra("category");

        btnAddFood.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String serName = edtServingName.getText().toString();
            String size = edtSize.getText().toString();
            String calories = edtCalories.getText().toString();
            if (name.isEmpty() || serName.isEmpty() || size.isEmpty() || calories.isEmpty()) {
                Toast.makeText(AddFoodActivity.this, "Fill All fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int cal = Integer.parseInt(calories);
            DietItems dietItems = new DietItems(name, serName, cal, size);
            databaseHelper.addDietsItems(category, dietItems).addOnSuccessListener(suc -> {
                Toast.makeText(AddFoodActivity.this, "DataAdded", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AddFoodActivity.this, DietsActivity.class);
//                startActivity(intent);
                finish();

            }).addOnFailureListener(er -> {
                Toast.makeText(AddFoodActivity.this, "Error", Toast.LENGTH_SHORT).show();

            });

        });
    }
}