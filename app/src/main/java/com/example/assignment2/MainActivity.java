package com.example.assignment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    dayNetIncomeAdapter dayNetIncomeAdapter;
    List<dayNetIncome> dayList;
    RecyclerView recyViewList;
    Button btnAdd;
    Button btnOver;
    Button btnList;
    DBManager dbManager = new DBManager(this);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find component

        recyViewList = findViewById(R.id.recyViewList);
        btnAdd = findViewById(R.id.btnAddList);
        btnOver = findViewById(R.id.btnOverviewList);
        btnList = findViewById(R.id.btnListList);

        //Fill recycler view
        setData();
        dayNetIncomeAdapter = new dayNetIncomeAdapter(dayList, MainActivity.this);
        recyViewList.setAdapter(dayNetIncomeAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyViewList.setLayoutManager(layoutManager);

        //Button click
        btnList.setEnabled(false);
        final Intent addIntent = new Intent(this, AddActivity.class);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(addIntent, 1);
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

    public void setData(){
        //Get data in past thirty days
        dayList = new ArrayList<>();
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        double total = 0.0;
        for(int i = 0; i < 30; i++){
            //if day = 0, go to last month, if month = 0, go to last year's december
            if (tempDay == 0){
                if (tempMonth == 0){
                    tempYear--;
                    tempMonth = 11;
                    tempDay = 31;
                }else{
                    tempMonth--;
                    cal.set(Calendar.MONTH, tempMonth);
                    tempDay = cal.get(Calendar.DAY_OF_MONTH);
                }
            }
            List<entry> entryList = dbManager.queryByDay(tempYear, tempMonth, tempDay);
            //if no entries, go to the day before
            if(entryList.size() == 0){
                tempDay--;
                continue;
            }else{
                //calculate total number of money
                for(int j = 0; j < entryList.size(); j++){
                    entry entry = entryList.get(j);
                    if(entry.getIO() == 0){
                        total += entry.getNumber();
                    }else{
                        total -= entry.getNumber();
                    }
                }
                dayNetIncome day = new dayNetIncome(tempMonth, tempDay, total, entryList);
                dayList.add(day);
            }
            tempDay--;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setData();
            dayNetIncomeAdapter.setDayList(dayList);
            dayNetIncomeAdapter.notifyDataSetChanged();
        }
    }
}
