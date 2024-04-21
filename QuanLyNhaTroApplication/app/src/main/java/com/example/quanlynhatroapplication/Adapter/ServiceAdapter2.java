package com.example.quanlynhatroapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatroapplication.Class.Service;
import com.example.quanlynhatroapplication.R;

import java.util.List;
import java.util.zip.Inflater;

public class ServiceAdapter2 extends RecyclerView.Adapter<ServiceAdapter2.viewholder> {
    private Context context;
    List<Service> items;

    public ServiceAdapter2(Context context, List<Service> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ServiceAdapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflater = LayoutInflater.from(context).inflate(R.layout.viewholder_service_3, parent, false);

        return new ServiceAdapter2.viewholder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter2.viewholder holder, int position) {
        Service service = items.get(position);
        holder.txtGiaDichVu.setText(service.getGia()+"đ/tháng");
        holder.txtTenDichVu.setText(service.getTenDichVu());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtGiaDichVu, txtTenDichVu;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtGiaDichVu =  itemView.findViewById(R.id.textViewGiaDichVu);
            txtTenDichVu = itemView.findViewById(R.id.textViewTenDichVu);
        }
    }
}
