package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.quanlynhatroapplication.Class.Food;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ListCartAdapter extends RecyclerView.Adapter<ListCartAdapter.viewholder> {
    Context context;
    List<Food> items;

    public ListCartAdapter(Context context, List<Food> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListCartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCartAdapter.viewholder holder, int position) {
        holder.txtTitle.setText(items.get(position).getName());
        holder.txtTime.setText(String.valueOf(items.get(position).getTimeProcessing()));
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()));
        holder.txtScore.setText(String.valueOf(items.get(position).getScore()));

        Glide.with(context)
                .load(items.get(position).getLinkImage())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtTime, txtPrice, txtScore;
        ImageView imgHinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.textViewTenPhong);
            txtTime = itemView.findViewById(R.id.textViewGiaTien);
            txtPrice = itemView.findViewById(R.id.textViewPriceCart);
            txtScore = itemView.findViewById(R.id.textViewDonVi);
            imgHinh = itemView.findViewById(R.id.imageViewHinhDV);

        }


    }
}
