package com.example.newanimals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;

import java.util.List;

public class AddsAdapter extends RecyclerView.Adapter<AddsAdapter.LabelHolder> {
    private Context context;
    private List<AdsData> data;

    public AddsAdapter(Context context, List<AdsData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AddsAdapter.LabelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ads_layout_item,parent,false);
        return new LabelHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddsAdapter.LabelHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LabelHolder extends RecyclerView.ViewHolder {
        public LabelHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
