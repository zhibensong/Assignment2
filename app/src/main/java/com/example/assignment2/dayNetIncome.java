package com.example.assignment2;

import java.util.Date;
import java.util.List;

public class dayNetIncome {
    private Date date;
    private Double total;
    private List<entry> entryList;
    public dayNetIncome(Date date, Double total, List<entry> entryList){
        this.date = date;
        this.total = total;
        this.entryList = entryList;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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
