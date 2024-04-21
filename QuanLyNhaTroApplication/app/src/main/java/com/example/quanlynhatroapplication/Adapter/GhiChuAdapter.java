package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.GhiChu;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.GhiChuActivity;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class GhiChuAdapter extends RecyclerView.Adapter<GhiChuAdapter.viewholder> {
    GhiChuActivity context;
    List<GhiChu> items;

    public GhiChuAdapter(GhiChuActivity context, List<GhiChu> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public GhiChuAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_ghichu, parent, false);
        return new GhiChuAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GhiChuAdapter.viewholder holder, int position) {
        GhiChu ghiChu = items.get(position);
        holder.txtTieuDe.setText(ghiChu.getTieuDe());
        holder.txtNoiDung.setText(ghiChu.getNoiDung());
        holder.txtThoiGian.setText(ghiChu.getThoiGian());

        if(ghiChu.getMauSac().equals("Lavender")) holder.layoutGhiChu.setBackgroundResource(R.color.lavender);
        if(ghiChu.getMauSac().equals("Vàng")) holder.layoutGhiChu.setBackgroundResource(R.color.vang);
        if(ghiChu.getMauSac().equals("Xanh Lá")) holder.layoutGhiChu.setBackgroundResource(R.color.xanhla);
        if(ghiChu.getMauSac().equals("Xanh Dương")) holder.layoutGhiChu.setBackgroundResource(R.color.xanhduong);
        if(ghiChu.getMauSac().equals("Đỏ")) holder.layoutGhiChu.setBackgroundResource(R.color.maudo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDialogSuaGhiChu();
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDiaLogXoaGhiChu();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtNoiDung, txtTieuDe, txtThoiGian;
        ConstraintLayout layoutGhiChu;
        ImageView btnXoa;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe =  itemView.findViewById(R.id.textViewTieuDe);
            txtNoiDung = itemView.findViewById(R.id.textViewNoiDung);
            txtThoiGian = itemView.findViewById(R.id.textViewThoiGian);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            layoutGhiChu = itemView.findViewById(R.id.layoutGhiChu);
        }
    }
}
