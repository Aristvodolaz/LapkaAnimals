package com.example.newanimals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.network.response.DaDataResponse;

import java.util.ArrayList;
import java.util.List;

public class AddressAutoAdapter extends RecyclerView.Adapter<AddressAutoAdapter.AddressHolder> {
    private List<DaDataResponse.DaDataResult> data;
    private Context context;
    private OnSuggestAddressClick onClick;

    public interface OnSuggestAddressClick {
        void onSuggestClick(String mainAddress);
    }

    public AddressAutoAdapter(Context context, OnSuggestAddressClick onClick) {
        this.context = context;
        this.data = new ArrayList<>();
        this.onClick = onClick;
    }

    public void setData(List<DaDataResponse.DaDataResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        AddressHolder holder = new AddressHolder(v, v1 -> {
            onClick.onSuggestClick(data.get((int)v.getTag()).getValue());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.mainAddress.setText(data.get(position).getValue());
        String str = data.get(position).getData().getCity() == null ? data.get(position).getData().getCountry() : data.get(position).getData().getCity() + ", " + data.get(position).getData().getCountry();
        holder.additionalAddress.setText(str);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mainAddress;
        TextView additionalAddress;
        View.OnClickListener listener;

        public AddressHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mainAddress = itemView.findViewById(R.id.main_address);
            additionalAddress = itemView.findViewById(R.id.additional_address);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            v.setTag(getAdapterPosition());
            listener.onClick(v);
        }
    }
}
