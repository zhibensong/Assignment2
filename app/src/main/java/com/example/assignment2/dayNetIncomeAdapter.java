package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dayNetIncomeAdapter extends RecyclerView.Adapter<dayNetIncomeAdapter.dayNetIncomeViewHolder>{

    private List<dayNetIncome> dayList;
    private Context context;
    String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Auc", "Sep", "Oct", "Nov", "Dec"};

    public dayNetIncomeAdapter(List<dayNetIncome> dayList, Context context){
        this.dayList = dayList;
        this.context = context;
    }

    public void setDayList(List<dayNetIncome> dayList) {
        this.dayList = dayList;
    }

    public class dayNetIncomeViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDate;
        public TextView txtTotal;
        public RecyclerView recyViewListItem;
        public entryAdapter entryAdapter;
        public RecyclerView.LayoutManager layoutManager;

        public dayNetIncomeViewHolder(Context context, View view) {
            super(view);
            txtDate = view.findViewById(R.id.txtDate);
            txtTotal = view.findViewById(R.id.txtTotal);
            recyViewListItem = view.findViewById(R.id.recyViewListItem);
            entryAdapter = new entryAdapter(context);
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyViewListItem.setLayoutManager(layoutManager);
        }

        public void showEntry(List<entry> entryList){
            entryAdapter.setEntry(entryList);
            recyViewListItem.setAdapter(entryAdapter);
        }
    }

    @NonNull
    @Override
    public dayNetIncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new dayNetIncomeViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull dayNetIncomeViewHolder holder, final int position){
        if(dayList.get(position).getTotal() > 0){
            holder.txtTotal.setText("+ " + String.valueOf(dayList.get(position).getTotal()));
        }
        holder.txtDate.setText(monthName[dayList.get(position).getMonth()] + "  " + String.valueOf(dayList.get(position).getDay()));

        holder.showEntry(dayList.get(position).getEntryList());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }
}
