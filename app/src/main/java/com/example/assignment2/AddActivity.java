package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    Integer[] incomeImageList = {R.drawable.salary, R.drawable.investment, R.drawable.parttime, R.drawable.borrow, R.drawable.selling, R.drawable.gift, R.drawable.other};
    String[] incomeNameList = {"Salary", "Investment income", "Internship", "Borrow", "Selling", "Gift", "Other in"};
    List<expenseType> incomeList = new ArrayList<expenseType>();
    Integer[] outgoingImageList = {R.drawable.eating, R.drawable.grocery, R.drawable.utilities, R.drawable.shopping, R.drawable.tax, R.drawable.travel, R.drawable.transport, R.drawable.business, R.drawable.education, R.drawable.health, R.drawable.entertainment, R.drawable.gift, R.drawable.borrow, R.drawable.other};
    String[] outgoingNameList = {"Eating out", "Groceries", "Utilities", "Shopping", "Tax", "Travel", "Transport", "Business", "Education", "Health", "Game", "Gift", "Pay back", "Other out"};
    List<expenseType> outgoingList = new ArrayList<expenseType>();
    RecyclerView recyViewIncome;
    RecyclerView recyViewOutgoing;
    TextView txtMoneySign;
    Button btnIncome;
    Button btnOutgoing;
    Button btnFinish;
    Button btnCancel;
    EditText txtMoney;
    int selectedIncome = 0;
    int selectedOutgoing = 0;
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    DBManager dbManager = new DBManager(this);
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
        btnFinish = findViewById(R.id.btnFinish);
        btnCancel = findViewById(R.id.btnCancel);
        txtMoney = findViewById(R.id.txtMoney);

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
                selectedIncome = pos;
                incomeAdapter.setPosition(pos);
                incomeAdapter.notifyDataSetChanged();
            }
        });
        outgoingAdapter.setOnItemClickListener(new expenseTypeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                selectedOutgoing = pos;
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtMoney.getText())){
                    Log.w("Warning", "Please input money amount");
                }else{
                    if(recyViewIncome.getVisibility() == View.VISIBLE){
                        dbManager.insert(0, incomeImageList[selectedIncome], incomeNameList[selectedIncome], Double.parseDouble(txtMoney.getText().toString()), year, month, day);
                    }else{
                        dbManager.insert(1, outgoingImageList[selectedOutgoing], outgoingNameList[selectedOutgoing], Double.parseDouble(txtMoney.getText().toString()), year, month, day);
                    }
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
