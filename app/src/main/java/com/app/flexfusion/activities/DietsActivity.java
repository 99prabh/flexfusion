package com.app.flexfusion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.adapters.DietsAdapter;
import com.app.flexfusion.fragments.DietPlansFragment;
import com.app.flexfusion.models.DietItems;
import com.app.flexfusion.repositories.DatabaseHelper;
import com.app.flexfusion.repositories.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DietsActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    EditText edtSearchBar;

    DietsAdapter dietsAdapter;

    List<DietItems> list;

    TextView tvText;

    ImageView ivAdd;
    ImageView ivBack;

    String category;

    DatabaseHelper databaseHelper;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        recyclerView = findViewById(R.id.recyclerViewDiets);
        ivBack = findViewById(R.id.ivBack);
        ivAdd = findViewById(R.id.ivAddFood);
        tvText = findViewById(R.id.tv_text);
        edtSearchBar = findViewById(R.id.edtSearchBar);

        list = new ArrayList<>();
        databaseHelper = new DatabaseHelper();

        auth = FirebaseAuth.getInstance();

        String uid = auth.getUid();

        category = DietPlansFragment.activityName;

        ivBack.setOnClickListener(view -> finish());

        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for your implementation
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dietsAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for your implementation
            }
        });

        if (Utils.isAdmin) {
            ivAdd.setVisibility(View.VISIBLE);
            ivAdd.setOnClickListener(view -> {
                Intent intent = new Intent(DietsActivity.this, AddFoodActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            });
        }
        tvText.setText(String.valueOf(DietPlansFragment.activityName));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        dietsAdapter = new DietsAdapter(this, list, category);
        recyclerView.setAdapter(dietsAdapter);

        databaseHelper.getFoodData(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DietItems dietItems = dataSnapshot.getValue(DietItems.class);
                    list.add(dietItems);
                    dietsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}