package com.example.navegandoentrefragmentoseadicionandotexto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonthRecyclerViewAdapter extends RecyclerView.Adapter<MonthRecyclerViewAdapter.ViewHolder> {

    private List<String> items;

    public MonthRecyclerViewAdapter(List<String> items) {
        this.items = items;
    }


    public void addItem(String item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);  // Notificar o adaptador que um novo item foi inserido
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}

