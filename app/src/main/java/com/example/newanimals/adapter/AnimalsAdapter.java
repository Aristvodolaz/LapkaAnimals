package com.example.newanimals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.LabelHolder> {
    private Context context;
    private List<AdsData> data;

    public AnimalsAdapter(Context context, List<AdsData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AnimalsAdapter.LabelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.animals_item, parent, false);
        return new LabelHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalsAdapter.LabelHolder holder, int position) {
        Picasso.get().load(data.get(position).getImgUrl()).into(holder.imageAnimals);
        holder.name.setText(data.get(position).getName_animals());
        holder.date.setText(data.get(position).getDate_lose());
        holder.location.setText(data.get(position).getAddress());
        holder.imagePol.setImageResource(data.get(position).getPol().equals("0") ? R.drawable.pol_girl : R.drawable.pol_girl); // todo changed new image
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LabelHolder extends RecyclerView.ViewHolder {
        ImageView imageAnimals, imagePol;
        TextView name, location, date;
        public LabelHolder(@NonNull View itemView) {
            super(itemView);
            imageAnimals = itemView.findViewById(R.id.image);
            imagePol = itemView.findViewById(R.id.pol);
            name = itemView.findViewById(R.id.name_cat);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
        }
    }
}
