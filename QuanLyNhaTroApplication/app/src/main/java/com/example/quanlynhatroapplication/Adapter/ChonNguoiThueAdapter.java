package com.example.quanlynhatroapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.AddNguoiThueActivity;
import com.example.quanlynhatroapplication.Class.Customer;
import com.example.quanlynhatroapplication.R;

import java.util.List;

public class ChonNguoiThueAdapter extends RecyclerView.Adapter<ChonNguoiThueAdapter.viewholder> {
    AddNguoiThueActivity context;
    List<Customer> items;

    public ChonNguoiThueAdapter(AddNguoiThueActivity context, List<Customer> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ChonNguoiThueAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_chon_nguoithue, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonNguoiThueAdapter.viewholder holder, int position) {
        holder.txtHoTen.setText(items.get(position).getHoTen());
        holder.txtEmail.setText(items.get(position).getEmail());
        holder.txtSoDienThoai.setText(items.get(position).getSdt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.chonNguoiThue(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView txtHoTen, txtSoDienThoai, txtEmail;
        ImageView imgHinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            txtHoTen = itemView.findViewById(R.id.textViewTenPhong);
            txtEmail = itemView.findViewById(R.id.textViewTang);
            txtSoDienThoai = itemView.findViewById(R.id.textViewSoNguoi);
            imgHinh = itemView.findViewById(R.id.imageViewHinh);
        }


    }
}
