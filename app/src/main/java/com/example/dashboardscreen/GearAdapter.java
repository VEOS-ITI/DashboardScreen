package com.example.dashboardscreen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GearAdapter extends RecyclerView.Adapter<GearAdapter.ViewHolder> {
    private String[] gears = {"P", "R", "N", "D"};
    private int selectedIndex = 3; // "D" as default

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView gearText;

        public ViewHolder(View view) {
            super(view);
            gearText = view.findViewById(R.id.gearText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gear, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String gear = gears[position];
        holder.gearText.setText(gear);

        if (position == selectedIndex) {
            holder.gearText.setTextColor(Color.WHITE);
            holder.gearText.setTextSize(36);
            holder.gearText.setTypeface(null, Typeface.BOLD);
        } else {
            holder.gearText.setTextColor(Color.parseColor("#80FFFFFF"));
            holder.gearText.setTextSize(28);
            holder.gearText.setTypeface(null, Typeface.NORMAL);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedIndex = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return gears.length;
    }
}
