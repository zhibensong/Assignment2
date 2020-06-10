package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<dayNetIncome> dayList = new ArrayList<>();
    List<Date> dateList = new ArrayList<>();
    List<Double> totalList = new ArrayList<>();
    List<entry> firstDay = new ArrayList<>();
    List<entry> secondDay = new ArrayList<>();
    List<List<entry>> entryListList = new ArrayList<>();
    RecyclerView recyViewList;
    Button btnAdd;
    Button btnOver;
    Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find component
        recyViewList = findViewById(R.id.recyViewList);
        btnAdd = findViewById(R.id.btnAddList);
        btnOver = findViewById(R.id.btnOverviewList);
        btnList = findViewById(R.id.btnListList);

        //Data Part
        dateList.add(new Date());
        dateList.add(new Date(1591620407));
        entry fe = new entry(R.drawable.salary, "Salary", 800.0);
        entry se = new entry(R.drawable.eating, "Eating out", -60.0);
        entry te = new entry(R.drawable.salary, "Salary", 800.0);
        entry foe = new entry(R.drawable.education, "Education", -100.0);
        firstDay.add(fe);
        firstDay.add(se);
        secondDay.add(te);
        secondDay.add(foe);
        entryListList.add(firstDay);
        entryListList.add(secondDay);
        totalList.add(fe.getNumber() + se.getNumber());
        totalList.add(te.getNumber() + foe.getNumber());
        for(int i = 0; i < dateList.size(); i++){
            dayNetIncome day = new dayNetIncome(dateList.get(i), totalList.get(i), entryListList.get(i));
            dayList.add(day);
        }

        //Fill recycler view
        dayNetIncomeAdapter dayNetIncomeAdapter = new dayNetIncomeAdapter(dayList, MainActivity.this);
        recyViewList.setAdapter(dayNetIncomeAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyViewList.setLayoutManager(layoutManager);

        //Button click
        btnList.setEnabled(false);
        final Intent addIntent = new Intent(this, AddActivity.class);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addIntent);
            }
        });
        final Intent overIntent = new Intent(this, OverviewActivity.class);
        btnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(overIntent);
            }
        });
    }
}
