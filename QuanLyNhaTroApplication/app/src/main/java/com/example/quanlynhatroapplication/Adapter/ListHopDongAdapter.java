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
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Bill;
import com.example.quanlynhatroapplication.Class.HopDong;
import com.example.quanlynhatroapplication.HopDongActivity;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ListHopDongAdapter extends RecyclerView.Adapter<ListHopDongAdapter.viewholder> {
    HopDongActivity context;
    List<HopDong> items;

    public ListHopDongAdapter(HopDongActivity context, List<HopDong> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListHopDongAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_hopdong, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHopDongAdapter.viewholder holder, int position) {
        HopDong hopDong = items.get(position);
        holder.txtMaHopDong.setText(items.get(position).getId());
        holder.txtTenNha.setText(items.get(position).getTenNha());
        holder.txtTenPhong.setText(items.get(position).getTenPhong());
        holder.txtNguoiDaiDien.setText(items.get(position).getHoTen());
        holder.txtNgayTao.setText(items.get(position).getNgayKyHopDong());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog(hopDong);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtMaHopDong, txtTenNha, txtTenPhong, txtNguoiDaiDien, txtNgayTao;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtMaHopDong = itemView.findViewById(R.id.textViewMaHD);
            txtTenNha = itemView.findViewById(R.id.textViewTenNha);
            txtTenPhong = itemView.findViewById(R.id.textViewTenPhong);
            txtNguoiDaiDien = itemView.findViewById(R.id.textViewNguoiDaiDien);
            txtNgayTao = itemView.findViewById(R.id.textViewNgayTao);
        }
    }

    private void showBottomDialog(HopDong hopDong) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_layout);

        LinearLayout layoutChiTiet = dialog.findViewById(R.id.layoutChiTiet);
        LinearLayout layoutChinhSua = dialog.findViewById(R.id.layoutChinhSua);
        LinearLayout layoutXoa = dialog.findViewById(R.id.layoutXoa);

        layoutChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Chi tiet hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogChinhSuaHopDong(hopDong);
            }
        });

        layoutChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Chinh sua hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogChinhSuaHopDong(hopDong);            }
        });

        layoutXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                //Toast.makeText(context.getContext(),"Xoa hoa don",Toast.LENGTH_SHORT).show();
                context.openDialogXoaHopDong(hopDong);            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
