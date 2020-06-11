package com.example.assignment2;

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

    List<entry> entryList = new ArrayList<>();
    List<Integer> imageList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Double> numberList = new ArrayList<>();
    Calendar cal = Calendar.getInstance();
    String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Auc", "Sep", "Oct", "Nov", "Dec"};
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int day;
    int[] amount;
    String[] label;
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
        overviewTitle.setText("Overview(" + monthName[month - 1] + ")");

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

        //Draw line chart
        setData();
        initChart();

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

    public int getMonthDay(int year, int month){
        if(month == 2){
            if(year % 4 != 0){
                return 28;
            }else if(year % 100 == 0 && year % 400 != 0){
                return 28;
            }else{
                return 29;
            }
        }else if (month <= 7){
            if (month % 2 == 0){
                return 30;
            }else{
                return 31;
            }
        }else if(month % 2 == 0){
            return 31;
        }else{
            return 30;
        }
    }

    //Set line chart data
    public void setData(){
        day = getMonthDay(year, month);
        amount = new int[day];
        label = new String[day];
        for(int i = 0; i < day; i++){
            amount[i] = 0;
            label[i] = String.valueOf(i + 1);
        }
        amount[9] = 300;
        amount[19] = 600;
        amount[29] = 900;
        for(int i = 0; i < day; i++){
            pointValues.add(new PointValue(i, amount[i]));
            axisValues.add(new AxisValue(i).setLabel(label[i]));
            System.out.println(axisValues.get(i));
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
        axisX.setName(monthName[month - 1]);
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
