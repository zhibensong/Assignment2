package com.example.assignment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class OverviewActivity extends AppCompatActivity {

    Integer[] incomeImageList = {R.drawable.salary, R.drawable.investment, R.drawable.parttime, R.drawable.borrow, R.drawable.selling, R.drawable.gift, R.drawable.other};
    String[] incomeNameList = {"Salary", "Investment income", "Internship", "Borrow", "Selling", "Gift", "Other in"};
    Integer[] outgoingImageList = {R.drawable.eating, R.drawable.grocery, R.drawable.utilities, R.drawable.shopping, R.drawable.tax, R.drawable.travel, R.drawable.transport, R.drawable.business, R.drawable.education, R.drawable.health, R.drawable.entertainment, R.drawable.gift, R.drawable.borrow, R.drawable.other};
    String[] outgoingNameList = {"Eating out", "Groceries", "Utilities", "Shopping", "Tax", "Travel", "Transport", "Business", "Education", "Health", "Game", "Gift", "Pay back", "Other out"};
    List<entry> entryList = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    DBManager dbManager = new DBManager(this);
    String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Auc", "Sep", "Oct", "Nov", "Dec"};
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    double[] amount;
    String[] label;
    entryAdapter entryAdapter;
    List<PointValue> pointValues = new ArrayList<>();
    List<AxisValue> axisValues = new ArrayList<>();
    LineChartView lineChart;
    RecyclerView recyViewList;
    TextView overviewTitle;
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
        lineChart = findViewById(R.id.lineChart);
        recyViewList = findViewById(R.id.recyViewList);
        overviewTitle = findViewById(R.id.overviewTitle);
        overviewTitle.setText("Overview(" + monthName[month] + ")");

        //Draw line chart
        setChartData();
        initChart();

        //Fill recycler view
        setListData();
        entryAdapter = new entryAdapter(OverviewActivity.this);
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
                startActivityForResult(addIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setListData();
            setChartData();
            initChart();
            entryAdapter.setEntry(entryList);
            entryAdapter.notifyDataSetChanged();
        }
    }

    public void setListData(){
        for (int i = 0; i < incomeNameList.length; i++){
            List<entry> entryIncome = dbManager.queryByType(0, incomeNameList[i], year, month);
            double temp = 0;
            if(entryIncome.size() > 0){
                for(int j = 0; j < entryIncome.size(); j++){
                    temp += entryIncome.get(j).getNumber();
                }
                entry entry = new entry(incomeImageList[i], incomeNameList[i], temp);
                entry.setIO(0);
                entryList.add(entry);
            }
        }
        for(int i = 0; i < outgoingNameList.length; i++){
            List<entry> entryOutgoing = dbManager.queryByType(1, outgoingNameList[i], year, month);
            double temp = 0;
            if(entryOutgoing.size() > 0){
                for(int j = 0; j < entryOutgoing.size(); j++){
                    temp += entryOutgoing.get(j).getNumber();
                }
                entry entry = new entry(outgoingImageList[i], outgoingNameList[i], temp);
                entry.setIO(1);
                entryList.add(entry);
            }
        }
    }

    //Set line chart data
    public void setChartData(){
        amount = new double[maxDay];
        label = new String[maxDay];
        for(int i = 1; i < maxDay + 1; i++){
            List<entry> entries = dbManager.queryOutgoing(year, month, i);
            double temp = 0;
            for(int j = 0; j < entries.size(); j++){
                temp += entries.get(j).getNumber();
            }
            amount[i - 1] = temp;
            label[i - 1] = String.valueOf(i);
        }
        for(int i = 0; i < maxDay; i++){
            pointValues.add(new PointValue(i, (float)amount[i]));
            axisValues.add(new AxisValue(i).setLabel(label[i]));
        }
    }

    //Initialize Line Chart
    public void initChart(){
        Line line = new Line(pointValues).setColor(Color.parseColor("#FFCD41"));
        List<Line> lines = new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //Axis X
        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(false);
        axisX.setTextColor(Color.BLACK);
        axisX.setName(monthName[month]);
        axisX.setTextSize(13);
        axisX.setValues(axisValues);
        axisX.setHasLines(true);
        data.setAxisXBottom(axisX);

        //Axis Y
        Axis axisY = new Axis();
        axisY.setName("Amount");
        axisY.setTextColor(Color.BLACK);
        axisY.setTextSize(13);
        data.setAxisYLeft(axisY);

        //Others
        lineChart.setInteractive(true);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }
}
