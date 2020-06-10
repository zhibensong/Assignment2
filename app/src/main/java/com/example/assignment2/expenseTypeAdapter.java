package com.example.assignment2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class expenseTypeAdapter extends RecyclerView.Adapter<expenseTypeAdapter.expenseTypeViewHolder> {

    private List<expenseType> typeList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int position;

    public expenseTypeAdapter(List<expenseType> typeList, Context context){
        this.typeList = typeList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    @NonNull
    @Override
    public expenseTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_item, parent, false);
        return new expenseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull expenseTypeViewHolder holder, final int position) {
        holder.imageType.setImageResource(typeList.get(position).getImage());
        holder.txtTypeName.setText(typeList.get(position).getName());
        if(holder != null){
            if(position == getPosition()){
                holder.itemView.setBackgroundColor(Color.parseColor("#fefaaa"));
            }else{
                holder.itemView.setBackgroundColor(Color.parseColor("#fafafa"));
            }
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class expenseTypeViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageType;
        private TextView txtTypeName;

        public expenseTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageType = itemView.findViewById(R.id.imageType);
            txtTypeName = itemView.findViewById(R.id.txtTypeName);
        }
    }
}
