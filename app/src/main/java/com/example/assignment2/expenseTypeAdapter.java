package com.example.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class expenseTypeAdapter extends RecyclerView.Adapter<expenseTypeAdapter.expenseTypeViewHOlder> {

    private List<expenseType> typeList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public expenseTypeAdapter(List<expenseType> typeList, Context context){
        this.typeList = typeList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    @NonNull
    @Override
    public expenseTypeViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_item, parent, false);
        return new expenseTypeViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull expenseTypeViewHOlder holder, final int position) {
        holder.imageType.setImageResource(typeList.get(position).getImage());
        holder.txtTypeName.setText(typeList.get(position).getName());
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

    public class expenseTypeViewHOlder extends RecyclerView.ViewHolder{

        private ImageView imageType;
        private TextView txtTypeName;

        public expenseTypeViewHOlder(@NonNull View itemView) {
            super(itemView);
            imageType = itemView.findViewById(R.id.imageType);
            txtTypeName = itemView.findViewById(R.id.txtTypeName);
        }
    }
}
