package com.example.assignment2;

import java.util.Date;
import java.util.List;

public class dayNetIncome {
    private int month;
    private int day;
    private Double total;
    private List<entry> entryList;
    public dayNetIncome(int month, int day, Double total, List<entry> entryList){
        this.month = month;
        this.day = day;
        this.total = total;
        this.entryList = entryList;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setEntryList(List<entry> entryList) {
        this.entryList = entryList;
    }

    public List<entry> getEntryList() {
        return entryList;
    }
}
