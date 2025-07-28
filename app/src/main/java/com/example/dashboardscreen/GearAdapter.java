package com.example.dashboardscreen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GearAdapter extends RecyclerView.Adapter<GearAdapter.GearViewHolder> {
    private final String[] gears = {"P", "R", "N", "D"};
    private int selectedPosition = 1; // default to "D"

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gear, parent, false);
        return new GearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GearViewHolder holder, int position) {
        holder.text.setText(gears[position]);

        if (position == selectedPosition) {
            holder.text.setTextSize(32);
            holder.text.setTextColor(Color.WHITE);
            holder.text.setAlpha(1.0f);
            holder.text.setShadowLayer(8, 0, 0, Color.WHITE); // glow
        } else {
            holder.text.setTextSize(24);
            holder.text.setTextColor(Color.parseColor("#55FFFFFF"));
            holder.text.setAlpha(0.5f);
            holder.text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return gears.length;
    }

    static class GearViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public GearViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.gearText);
        }
    }
}
