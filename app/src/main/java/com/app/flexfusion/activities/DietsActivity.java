package com.app.flexfusion.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.flexfusion.R;
import com.app.flexfusion.adapters.DietsAdapter;
import com.app.flexfusion.models.DietItems;

import java.util.ArrayList;
import java.util.List;

public class DietsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DietsAdapter dietsAdapter;

    List<DietItems>list;

    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        recyclerView=findViewById(R.id.recyclerViewDiets);
        tvText=findViewById(R.id.tv_text);
        list=new ArrayList<>();

        tvText.setText(String.valueOf(DietPlansFragment.activityName));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (getIntent() != null && getIntent().hasExtra("dataList")) {
           list = getIntent().getParcelableArrayListExtra("dataList");
        }
        dietsAdapter=new DietsAdapter(this,list);
        recyclerView.setAdapter(dietsAdapter);


    }
}