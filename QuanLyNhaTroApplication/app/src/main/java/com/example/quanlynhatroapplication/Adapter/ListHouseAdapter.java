package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.HouseDetailActivity;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ListHouseAdapter extends RecyclerView.Adapter<ListHouseAdapter.viewholder> {
    Context context;
    List<House> items;

    public ListHouseAdapter(Context context, List<House> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListHouseAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_house, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHouseAdapter.viewholder holder, int position) {
        holder.txtTenNha.setText(items.get(position).getTenNha());
        holder.txtDiachi.setText(items.get(position).getDiaChi());
        holder.txtThanhPho.setText(items.get(position).getTinhThanh());
        holder.txtQuanHuyen.setText(items.get(position).getQuanHuyen() + " ,");

        Glide.with(context)
                .load(items.get(position).getLinkHinh())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.imgHinh);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HouseDetailActivity.class);
                intent.putExtra("house", (House) items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTenNha, txtDiachi, txtQuanHuyen, txtThanhPho;
        ImageView imgHinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTenNha = itemView.findViewById(R.id.textViewTenNha);
            txtDiachi = itemView.findViewById(R.id.textViewDiaChi);
            txtQuanHuyen = itemView.findViewById(R.id.textViewQuanHuyen);
            txtThanhPho = itemView.findViewById(R.id.textViewThanhPho);
            imgHinh = itemView.findViewById(R.id.imageViewHinh);


        }


    }
}
