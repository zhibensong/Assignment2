package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class entryAdapter extends RecyclerView.Adapter<entryAdapter.entryViewHolder> {

    private List<entry> entryList;
    private Context context;

    public entryAdapter(Context context){
        this.context = context;
    }

    public void setEntry(List<entry> entryList){
        this.entryList = entryList;
    }

    public class entryViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageEntry;
        public TextView txtType;
        public TextView txtNumber;

        public entryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageEntry = itemView.findViewById(R.id.imageEntry);
            txtType = itemView.findViewById(R.id.txtType);
            txtNumber = itemView.findViewById(R.id.txtNumber);
        }
    }

    @NonNull
    @Override
    public entryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_content_item, parent, false);
        return new entryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull entryViewHolder holder, int position) {
        holder.imageEntry.setImageResource(entryList.get(position).getImage());
        holder.txtType.setText(entryList.get(position).getTypeName());
        holder.txtNumber.setText(entryList.get(position).getNumber() + "");
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
