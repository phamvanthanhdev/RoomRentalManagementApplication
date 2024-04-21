package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.NguoiThue;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.viewholder> {
    Context context;
    List<NguoiThue> items;
    List<String> tenPhongList;
    List<String> tenNhaList;

    public NguoiThueAdapter(Context context, List<NguoiThue> items, List<String> tenNhaList, List<String> tenPhongList) {
        this.context = context;
        this.items = items;
        this.tenPhongList = tenPhongList;
        this.tenNhaList = tenNhaList;
    }

    @NonNull
    @Override
    public NguoiThueAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_nguoithue, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiThueAdapter.viewholder holder, int position) {
        holder.txtHoTen.setText(items.get(position).getHoTen());
        holder.txtEmail.setText(items.get(position).getEmail());
        holder.txtSoDienThoai.setText(items.get(position).getSoDienThoai());
        holder.txtTenPhongTenNha.setText(tenPhongList.get(position)+" - " + tenNhaList.get(position));

        holder.btnDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + items.get(position).getSoDienThoai()));
                context.startActivity(intent);
            }
        });

        holder.btnTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + items.get(position).getSoDienThoai()));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.chonNguoiThue(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtHoTen, txtSoDienThoai, txtEmail, txtTenPhongTenNha;
        ImageView imgHinh, btnDienThoai, btnTinNhan;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtHoTen = itemView.findViewById(R.id.textViewTenPhong);
            txtEmail = itemView.findViewById(R.id.textViewTang);
            txtSoDienThoai = itemView.findViewById(R.id.textViewSoNguoi);
            txtTenPhongTenNha = itemView.findViewById(R.id.textViewTenPhongTenNha);
            imgHinh = itemView.findViewById(R.id.imageViewHinh);
            btnTinNhan = itemView.findViewById(R.id.imageViewTinNhan);
            btnDienThoai = itemView.findViewById(R.id.imageViewDienThoai);
        }


    }
}
