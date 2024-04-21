package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.quanlynhatroapplication.Class.Food;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class HouseAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Food> data;

    public HouseAdapter(@NonNull Context context, int resource, @NonNull List<Food> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txtTitle, txtTime, txtPrice, txtScore;
        ImageView imgHinh;
        txtTitle = convertView.findViewById(R.id.textViewTenNha);
        txtTime = convertView.findViewById(R.id.textViewDiaChi);
        txtPrice = convertView.findViewById(R.id.textViewThanhPho);
        txtScore = convertView.findViewById(R.id.textViewQuanHuyen);
        imgHinh = convertView.findViewById(R.id.imageViewHinh);

        Food food = data.get(position);
        txtTitle.setText(food.getName());
        txtTime.setText(String.valueOf(food.getTimeProcessing()));
        txtPrice.setText(String.valueOf(food.getPrice()));
        txtScore.setText(String.valueOf(food.getScore()));

        Glide.with(context)
                .load(food.getLinkImage())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(imgHinh);

        return convertView;
    }
}
