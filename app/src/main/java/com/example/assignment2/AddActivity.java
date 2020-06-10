package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    Integer[] incomeImageList = {R.drawable.salary, R.drawable.investment, R.drawable.parttime, R.drawable.borrow, R.drawable.selling, R.drawable.gift, R.drawable.other};
    String[] incomeNameList = {"Salary", "Investment income", "Internship", "Borrow", "Selling", "Gift", "Other"};
    List<expenseType> incomeList = new ArrayList<expenseType>();
    Integer[] outgoingImageList = {R.drawable.eating, R.drawable.grocery, R.drawable.utilities, R.drawable.shopping, R.drawable.tax, R.drawable.travel, R.drawable.transport, R.drawable.business, R.drawable.education, R.drawable.health, R.drawable.entertainment, R.drawable.gift, R.drawable.borrow, R.drawable.other};
    String[] outgoingNameList = {"Eating out", "Groceries", "Utilities", "Shopping", "Tax", "Travel", "Transport", "Business", "Education", "Health", "Game", "Gift", "Pay back", "Other"};
    List<expenseType> outgoingList = new ArrayList<expenseType>();
    RecyclerView recyViewIncome;
    RecyclerView recyViewOutgoing;
    TextView txtMoneySign;
    Button btnIncome;
    Button btnOutgoing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Find component
        recyViewIncome = findViewById(R.id.recyViewIncome);
        recyViewOutgoing = findViewById(R.id.recyViewOutgoing);
        btnIncome = findViewById(R.id.btnIncome);
        btnOutgoing = findViewById(R.id.btnOutgoing);
        txtMoneySign = findViewById(R.id.txtMoneySign);
        txtMoneySign.setText("+");

        //Data part
        for(int i = 0; i < incomeImageList.length; i++){
            incomeList.add(new expenseType(incomeImageList[i], incomeNameList[i]));
        }
        for(int i = 0; i < outgoingImageList.length; i++){
            outgoingList.add(new expenseType(outgoingImageList[i], outgoingNameList[i]));
        }

        //Fill recycler view
        final expenseTypeAdapter incomeAdapter = new expenseTypeAdapter(incomeList, AddActivity.this);
        final expenseTypeAdapter outgoingAdapter = new expenseTypeAdapter(outgoingList, AddActivity.this);
        incomeAdapter.setOnItemClickListener(new expenseTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                incomeAdapter.setPosition(pos);
                incomeAdapter.notifyDataSetChanged();
            }
        });
        outgoingAdapter.setOnItemClickListener(new expenseTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                outgoingAdapter.setPosition(pos);
                outgoingAdapter.notifyDataSetChanged();
            }
        });
        GridLayoutManager incomeLayout = new GridLayoutManager(this, 2);
        GridLayoutManager outgoingLayout = new GridLayoutManager(this, 2);
        recyViewIncome.setLayoutManager(incomeLayout);
        recyViewIncome.setAdapter(incomeAdapter);
        recyViewOutgoing.setLayoutManager(outgoingLayout);
        recyViewOutgoing.setAdapter(outgoingAdapter);

        recyViewOutgoing.setVisibility(View.INVISIBLE);
        btnOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMoneySign.setText("-");
                recyViewIncome.setVisibility(View.INVISIBLE);
                recyViewOutgoing.setVisibility(View.VISIBLE);
            }
        });
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMoneySign.setText("+");
                recyViewIncome.setVisibility(View.VISIBLE);
                recyViewOutgoing.setVisibility(View.INVISIBLE);
            }
        });
    }
}
