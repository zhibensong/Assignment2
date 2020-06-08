package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<dayNetIncome> dayList = new ArrayList<dayNetIncome>();
    List<Date> dateList = new ArrayList<Date>();
    List<Double> totalList = new ArrayList<Double>();
    List<entry> firstDay = new ArrayList<entry>();
    List<entry> secondDay = new ArrayList<entry>();
    List<List<entry>> entryListList = new ArrayList<List<entry>>();
    RecyclerView recyViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data Part
        dateList.add(new Date());
        dateList.add(new Date(1591620407));
        entry fe = new entry(R.drawable.add, "Salary", 800.0);
        entry se = new entry(R.drawable.cancel, "Food", -60.0);
        entry te = new entry(R.drawable.add, "Salary", 800.0);
        entry foe = new entry(R.drawable.cancel, "Movie", -100.0);
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

        recyViewList = findViewById(R.id.recyViewList);
        dayNetIncomeAdapter dayNetIncomeAdapter = new dayNetIncomeAdapter(dayList, MainActivity.this);
        recyViewList.setAdapter(dayNetIncomeAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyViewList.setLayoutManager(layoutManager);
    }
}
