package com.example.quanlynhatroapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.House;
import com.example.quanlynhatroapplication.Class.Room;
import com.example.quanlynhatroapplication.Fragment.BillFragment;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ChiTietThongKeAdapter extends RecyclerView.Adapter<ChiTietThongKeAdapter.viewholder> {
    Context context;
    List<Bill> items;
    List<House> houses;
    List<Room> rooms;

    public ChiTietThongKeAdapter(Context context, List<Bill> items, List<House> houses, List<Room> rooms){
        this.context = context;
        this.items = items;
        this.houses = houses;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ChiTietThongKeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_chitiet_thongke, parent, false);
        return new ChiTietThongKeAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietThongKeAdapter.viewholder holder, int position) {
        Bill bill = items.get(position);
        holder.txtThang.setText(bill.getThang());
        holder.txtMaHoaDon.setText(bill.getId());
        holder.txtTongTien.setText((bill.getTienPhong() + bill.getTienDichVu())+"đ");
        holder.txtTienPhong.setText(bill.getTienPhong() +"đ");
        holder.txtTienDichVu.setText(bill.getTienDichVu() +"đ");
        holder.txtTenNha.setText(houses.get(position).getTenNha());
        holder.txtTenPhong.setText(rooms.get(position).getTenPhong());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtThang, txtMaHoaDon, txtTongTien, txtTenNha, txtTenPhong, txtTienPhong, txtTienDichVu;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtThang =  itemView.findViewById(R.id.textViewThang);
            txtMaHoaDon =  itemView.findViewById(R.id.textViewMaHoaDon);
            txtTongTien =  itemView.findViewById(R.id.textViewTongTien);
            txtTienPhong =  itemView.findViewById(R.id.textViewTienPhong);
            txtTienDichVu =  itemView.findViewById(R.id.textViewTienDichVu);

            txtTenNha =  itemView.findViewById(R.id.textViewTenNha);
            txtTenPhong =  itemView.findViewById(R.id.textViewTenPhong);
        }
    }


}
