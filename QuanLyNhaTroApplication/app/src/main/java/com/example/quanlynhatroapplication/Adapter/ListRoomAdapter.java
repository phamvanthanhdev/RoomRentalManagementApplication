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
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.HouseDetailActivity;
import com.example.quanlynhatroapplication.R;
import com.example.quanlynhatroapplication.RoomDetailActivity;

import java.util.List;

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.viewholder> {
    Context context;
    List<Room> items;
    String houseId;

    public ListRoomAdapter(Context context, List<Room> items, String houseId) {
        this.context = context;
        this.items = items;
        this.houseId = houseId;
    }

    @NonNull
    @Override
    public ListRoomAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_room, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRoomAdapter.viewholder holder, int position) {
        holder.txtTenPhong.setText(items.get(position).getTenPhong());
        holder.txtTang.setText("Tầng : " + items.get(position).getTangSo());
        holder.txtSoNguoi.setText("Số người : 0/"+items.get(position).getGioiHanNguoiThue());
        holder.txtGiaPhong.setText(items.get(position).getGiaPhong() + " đ/tháng");

        Glide.with(context)
                .load(items.get(position).getLinkHinh())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.imgHinh);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomDetailActivity.class);
                intent.putExtra("room", items.get(position));
                intent.putExtra("houseId", houseId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTenPhong, txtTang, txtSoNguoi, txtGiaPhong;
        ImageView imgHinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTenPhong = itemView.findViewById(R.id.textViewTenPhong);
            txtTang = itemView.findViewById(R.id.textViewTang);
            txtSoNguoi = itemView.findViewById(R.id.textViewSoNguoi);
            txtGiaPhong = itemView.findViewById(R.id.textViewGiaPhong);
            imgHinh = itemView.findViewById(R.id.imageViewHinh);


        }


    }
}
