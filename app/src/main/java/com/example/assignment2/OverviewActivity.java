package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    List<entry> entryList = new ArrayList<>();
    List<Integer> imageList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Double> numberList = new ArrayList<>();
    RecyclerView recyViewList;
    Button btnList;
    Button btnAdd;
    Button btnOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Find component
        btnAdd = findViewById(R.id.btnAddOver);
        btnList = findViewById(R.id.btnListOver);
        btnOver = findViewById(R.id.btnOverviewOver);
        recyViewList = findViewById(R.id.recyViewList);

        //Data Part
        imageList.add(R.drawable.salary);
        imageList.add(R.drawable.eating);
        nameList.add("Salary");
        nameList.add("Eating out");
        numberList.add(1500.0);
        numberList.add(200.0);
        for(int i = 0; i < imageList.size(); i++){
            entry temp = new entry(imageList.get(i), nameList.get(i), numberList.get(i));
            entryList.add(temp);
        }

        //Fill recycler view
        entryAdapter entryAdapter = new entryAdapter(OverviewActivity.this);
        entryAdapter.setEntry(entryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyViewList.setAdapter(entryAdapter);
        recyViewList.setLayoutManager(layoutManager);

        //Button click
        btnOver.setEnabled(false);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent addIntent = new Intent(this, AddActivity.class);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addIntent);
            }
        });
    }
}
