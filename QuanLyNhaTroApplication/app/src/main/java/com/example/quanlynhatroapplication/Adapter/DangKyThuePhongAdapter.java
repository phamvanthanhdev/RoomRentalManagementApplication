package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.quanlynhatroapplication.Class.Customer;
import com.example.quanlynhatroapplication.Class.DangKyThuePhong;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.DangKyThuePhongActivity;
import com.example.quanlynhatroapplication.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DangKyThuePhongAdapter extends RecyclerView.Adapter<DangKyThuePhongAdapter.viewHolder> {
    DangKyThuePhongActivity context;
    List<DangKyThuePhong> items;
    List<House> nhaList;
    List<Room> phongList;
    List<Customer> khachHangList;

    public DangKyThuePhongAdapter(DangKyThuePhongActivity context, List<DangKyThuePhong> items, List<House> nhaList, List<Room> phongList, List<Customer> khachHangList) {
        this.context = context;
        this.items = items;
        this.nhaList = nhaList;
        this.phongList = phongList;
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public DangKyThuePhongAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_dangkythuephong, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DangKyThuePhongAdapter.viewHolder holder, int position) {
        DangKyThuePhong dangKyThuePhong = items.get(position);
        House nha = nhaList.get(position);
        Room phong = phongList.get(position);
        Customer khachHang = khachHangList.get(position);

        holder.txtTenPhong.setText(phong.getTenPhong());
        holder.txtTenNha.setText(nha.getTenNha());
        holder.txtThoiGian.setText(dangKyThuePhong.getThoiGian());
        holder.txtEmail.setText(khachHang.getEmail());
        Glide.with(context).load(phong.getLinkHinh()).into(holder.imgHinh);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(context, ChiTietPhongActivity.class);
                intent.putExtra("nha", nha);
                intent.putExtra("phong", phong);
                context.startActivity(intent);*/
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.XoaDangKyThuePhong(items.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView txtTenPhong, txtTenNha, txtThoiGian, txtEmail;
        Button btnXoa, btnThemNhanh;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            txtTenPhong = itemView.findViewById(R.id.tenPhong);
            txtTenNha = itemView.findViewById(R.id.tenNha);
            txtThoiGian = itemView.findViewById(R.id.thoiGian);
            txtEmail = itemView.findViewById(R.id.email);
            btnXoa = itemView.findViewById(R.id.buttonXoa);
            btnThemNhanh = itemView.findViewById(R.id.buttonThemNhanh);
        }
    }
}
