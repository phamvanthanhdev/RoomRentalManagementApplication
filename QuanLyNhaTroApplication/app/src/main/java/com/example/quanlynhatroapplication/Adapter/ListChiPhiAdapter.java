package com.example.quanlynhatroapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhatroapplication.ChiPhiActivity;
import com.example.quanlynhatroapplication.Class.ChiPhi;
import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;
import com.example.quanlynhatroapplication.ServiceActivity;

import java.util.List;

public class ListChiPhiAdapter extends RecyclerView.Adapter<ListChiPhiAdapter.viewholder> {
    ChiPhiActivity context;
    List<ChiPhi> items;

    public ListChiPhiAdapter(ChiPhiActivity context, List<ChiPhi> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListChiPhiAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_chiphi, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListChiPhiAdapter.viewholder holder, int position) {
        holder.txtTenChiPhi.setText(items.get(position).getTen());
        holder.txtNoiDung.setText(items.get(position).getNoiDung());
        holder.txtGiaTien.setText(items.get(position).getSoTien()+" đ");
        holder.txtThang.setText("("+items.get(position).getThang()+")");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.EditDichVu(items.get(position));
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.XoaDichVu(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtTenChiPhi, txtGiaTien, txtNoiDung, txtThang;
        ImageView imgHinh, btnXoa;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtTenChiPhi = itemView.findViewById(R.id.textViewTenChiPhi);
            txtGiaTien = itemView.findViewById(R.id.textViewGiaTien);
            txtNoiDung = itemView.findViewById(R.id.textViewNoiDung);
            txtThang = itemView.findViewById(R.id.textViewThang);

            imgHinh = itemView.findViewById(R.id.imageViewHinhDV);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }


    }
}
